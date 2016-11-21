package se.callista.rxjava;

import com.eclipsesource.json.Json;
import io.netty.buffer.ByteBuf;
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

	private static int DEFAULT_SPEED = 50; //(km/h)
	static Logger logger = LoggerFactory.getLogger(Application.class);

	private static Coordinate vallda = new Coordinate(57.482027, 11.993440);
	private static Coordinate callista = new Coordinate(57.705028, 11.992170);

	private static Map<Integer, Coordinate> trucks = new HashMap();

	static {
		trucks.put(1, vallda);
		trucks.put(2, callista);
	}

	public static void main(String[] args) {

		HttpServer<ByteBuf, ByteBuf> server;

		server = HttpServer.newServer(8070)
				.start((req, resp) -> {
					int speed = DEFAULT_SPEED;

					final List<String> truckIdParam = req.getQueryParameters().get("truckId");
					final List<String> speedParam = req.getQueryParameters().get("speed");
					final List<String> latParam = req.getQueryParameters().get("lat");
					final List<String> lngParam = req.getQueryParameters().get("lng");

					if (CollectionUtils.isEmpty(truckIdParam)) {
						throw new IllegalArgumentException("Missing truckId");
					}
					if (CollectionUtils.isEmpty(latParam)) {
						throw new IllegalArgumentException("Missing latitude");
					}
					if (CollectionUtils.isEmpty(lngParam)) {
						throw new IllegalArgumentException("Missing longitude");
					}

					if (CollectionUtils.isNotEmpty(speedParam)) {
						speed = Integer.parseInt(speedParam.get(0));
					}
					int truckId = Integer.parseInt(truckIdParam.get(0));
					double lat = Double.parseDouble(latParam.get(0));
					double lng = Double.parseDouble(lngParam.get(0));

					final Coordinate to = new Coordinate(lat, lng);
					final Coordinate from = trucks.get(truckId);

					logger.debug("Distance to destination: {}", distance(from, to));

					final List<Coordinate> waypoints = waypoints(from, to, toMetersPerSecond(speed));

					final Observable<String> stringObservable = Observable.interval(1, TimeUnit.SECONDS)
							.zipWith(Observable.from(waypoints), (tick, coordinate) ->
									Json.object()
											.add("id", truckId)
											.add("lat", coordinate.getLatitude())
											.add("long", coordinate.getLongitude()).toString() + "\n")
							.doOnNext(logger::debug);

					return resp.writeStringAndFlushOnEach(stringObservable);

				});
		server.awaitShutdown();

	}

}