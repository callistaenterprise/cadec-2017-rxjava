package se.callista.rxjava;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.List;

public class DroneSimulatorServer {
	private static Logger logger = LoggerFactory.getLogger(DroneSimulatorServer.class);
	private static Coordinate droneBaseStation = new Coordinate(57.706324, 11.963436);
	private static final int SPEED = 300;

	public static void main(String[] args) {

		final DroneSimulator droneSimulator = new DroneSimulator();

		HttpServer.newServer(8070).start((req, resp) -> {

			double lat = getRequestParamAsDouble(req, "lat");
			double lng = getRequestParamAsDouble(req, "lng");

			logger.debug("Lat: {}, Lng: {}", lat, lng);
			Coordinate to = new Coordinate(lat, lng);
			Trip trip = new Trip(droneBaseStation, to, SPEED);
			logger.debug("Distance to destination: {}", trip.getDistance());


			final Observable<String> droneSimulation = droneSimulator
					.simulateDroneTrip(trip, 100)
					.map(currentPosition -> toJson(currentPosition, trip))
					.map(json -> "data: " + json + "\n\n")
					.concatWith(Observable.just("event: stop\ndata:\n\n"))
					.doOnNext(logger::debug);

			return resp
					.setHeader("Access-Control-Allow-Origin", "*")
					.setHeader("Content-Type", "text/event-stream")
					.writeStringAndFlushOnEach(droneSimulation);

		}).awaitShutdown();
	}

	private static String toJson(Coordinate coordinate, Trip trip) {
		return "{\"lat\":" + coordinate.getLat() + ", \"lng\":" + coordinate.getLng() + ", \"eta\":" + Math.round(trip.getEta(coordinate)) + "}";
	}

	private static double getRequestParamAsDouble(HttpServerRequest<ByteBuf> req, String name) {
		final List<String> params = req.getQueryParameters().get(name);
		return Double.parseDouble(params.get(0));
	}

}