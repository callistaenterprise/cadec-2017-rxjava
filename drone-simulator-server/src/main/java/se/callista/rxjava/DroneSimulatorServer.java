package se.callista.rxjava;

import com.eclipsesource.json.Json;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.List;

public class DroneSimulatorServer {
	private static Logger logger = LoggerFactory.getLogger(DroneSimulatorServer.class);

	public static void main(String[] args) {

		final TripDatabase tripDatabase = new TripDatabase();
		final DroneSimulator droneSimulator = new DroneSimulator(tripDatabase);

		HttpServer.newServer(8070).start((req, resp) -> {
			logger.debug("Receiving request, using droneSimulator {}", droneSimulator);
			int droneId = getRequestParamAsInt(req, "droneId");

			logger.debug("Speed: {}, TruckId: {}, Lat: {}, Lng: {}", droneId);

			final Trip trip = tripDatabase.getTripByDrone(droneId);

			logger.debug("Distance to destination: {}", trip.getDistance());

			final Observable<String> droneSimulation = droneSimulator
					.simulateDroneTrip(droneId)
					.map(coordinate -> toJson(droneId, coordinate))
					.doOnNext(logger::debug);

			return resp.writeStringAndFlushOnEach(droneSimulation);

		}).awaitShutdown();
	}

	private static String toJson(int droneId, Coordinate coordinate) {
		return Json.object()
				.add("id", droneId)
				.add("lat", coordinate.getLatitude())
				.add("long", coordinate.getLongitude()).toString() + "\n";
	}

	private static int getRequestParamAsInt(HttpServerRequest<ByteBuf> req, String name) {
		final List<String> params = req.getQueryParameters().get(name);
		return Integer.parseInt(params.get(0));
	}
}