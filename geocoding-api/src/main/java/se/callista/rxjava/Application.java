package se.callista.rxjava;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	private static Map<String, JsonObject> address2Coordinates = new HashMap<>();

	static {
		address2Coordinates.put("Kungsbacka",
				Json.object()
						.add("lat", "57.486106")
						.add("long", "12.074773"));
		address2Coordinates.put("Mölnlycke",
				Json.object()
						.add("lat", "57.658945")
						.add("long", "12.117010"));
		address2Coordinates.put("Kungälv",
				Json.object()
						.add("lat", "57.871530")
						.add("long", "11.976075"));
	}


	public static void main(String[] args) {
		HttpServer.newServer(8050).start((req, resp) -> {

			String street = getRequestParamAsString(req, "street");
			String city = getRequestParamAsString(req, "city");
			logger.debug("Receiving request, get coordinates for {}, {}", street, city);

			return resp.writeString(Observable.just(address2Coordinates.get(city).toString()).delay(500, TimeUnit.MILLISECONDS));

		}).awaitShutdown();
	}

	private static String getRequestParamAsString(HttpServerRequest<ByteBuf> req, String name) {
		final List<String> params = req.getQueryParameters().get(name);
		return params.get(0);
	}
}
