package se.callista.rxjava;

import com.eclipsesource.json.Json;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpRequest;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import io.reactivex.netty.protocol.http.server.HttpServer;

import static se.callista.rxjava.GeoMath.distance;
import static se.callista.rxjava.GeoMath.toMetersPerSecond;
import static se.callista.rxjava.GeoMath.waypoints;

public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	private static Map<Integer, Coordinate> trucks = new HashMap();

	private static Coordinate vallda = new Coordinate(57.482027, 11.993440);
	private static Coordinate callista = new Coordinate(57.705028, 11.992170);

	static {
		trucks.put(1, vallda);
		trucks.put(2, callista);
	}

	public static void main(String[] args) {

		HttpServer<ByteBuf, ByteBuf> server;

		server = HttpServer.newServer(8070)
				.start((req, resp) -> {

					int speed = getRequestParamAsInt(req, "speed");
					int truckId = getRequestParamAsInt(req, "truckId");
					double lat = getRequestParamAsDouble(req, "lat");
					double lng = getRequestParamAsDouble(req, "lng");

					logger.debug("Speed: {}, TruckId: {}, Lat: {}, Lng: {}", speed, truckId, lat, lng);

					final Coordinate to = new Coordinate(lat, lng);
					final Coordinate from = trucks.get(truckId);

					logger.debug("Distance to destination: {}", distance(from, to));
					
					final List<Coordinate> waypoints = waypoints(from, to, toMetersPerSecond(speed));

					final Observable<String> waypointJson = Observable.interval(1, TimeUnit.SECONDS)
							.zipWith(Observable.from(waypoints), (tick, coordinate) ->
									Json.object()
											.add("id", truckId)
											.add("lat", coordinate.getLatitude())
											.add("long", coordinate.getLongitude()).toString() + "\n")
							.doOnNext(logger::debug);

					return resp.writeStringAndFlushOnEach(waypointJson);

				});
		server.awaitShutdown();

	}

	private static int getRequestParamAsInt(HttpServerRequest<ByteBuf> req, String name) {
		final List<String> params = req.getQueryParameters().get(name);
		return Integer.parseInt(params.get(0));
	}

	private static double getRequestParamAsDouble(HttpServerRequest<ByteBuf> req, String name) {
		final List<String> params = req.getQueryParameters().get(name);
		return Double.parseDouble(params.get(0));
	}
}