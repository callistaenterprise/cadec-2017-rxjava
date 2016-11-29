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
	private static Coordinate droneBaseStation = new Coordinate(57.703472, 11.992584);
	private static final int SPEED = 150;

	public static void main(String[] args) {

		final TripDatabase tripDatabase = new TripDatabase();
		final DroneSimulator droneSimulator = new DroneSimulator(tripDatabase);

		HttpServer.newServer(8070).start((req, resp) -> {

			double lat = getRequestParamAsDouble(req, "lat");
			double lng = getRequestParamAsDouble(req, "long");

			logger.debug("Lat: {}, Long: {}", lat, lng);
			Coordinate to = new Coordinate(lat, lng);

			Trip trip;

			if (tripDatabase.containsTripTo(to)) {
				trip = tripDatabase.getTripTo(to);
			} else {
				trip = new Trip(droneBaseStation, to, SPEED);
				tripDatabase.addTrip(trip);
			}

			logger.debug("Distance to destination: {}", trip.getDistance());

			final Observable<String> droneSimulation = droneSimulator
					.simulateDroneTrip(trip)
					.map(coordinate -> toJson(coordinate))
					.doOnNext(logger::debug);

			return resp.writeStringAndFlushOnEach(droneSimulation);

		}).awaitShutdown();
	}

	private static String toJson(Coordinate coordinate) {
		return Json.object()
//				.add("id", droneId)
				.add("lat", coordinate.getLatitude())
				.add("long", coordinate.getLongitude()).toString() + "\n";
	}

	private static double getRequestParamAsDouble(HttpServerRequest<ByteBuf> req, String name) {
		final List<String> params = req.getQueryParameters().get(name);
		return Double.parseDouble(params.get(0));
	}
}