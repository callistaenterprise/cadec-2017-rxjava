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

	private static Map<String, JsonObject> pnr2Address = new HashMap<>();

	static {
		pnr2Address.put("1212121212",
				Json.object()
						.add("street", "Storgatan 1")
						.add("city", "Kungsbacka"));
		pnr2Address.put("1212121212",
				Json.object()
						.add("street", "Biblioteksgatan 1")
						.add("city", "Mölnlycke"));
		pnr2Address.put("9402052386",
				Json.object()
						.add("street", "Parkgatan 1")
						.add("city", "Kungälv"));
	}


	public static void main(String[] args) {
		HttpServer.newServer(8060).start((req, resp) -> {

			String pnr = getRequestParamAsString(req, "pnr");
			logger.debug("Receiving request, get address for {}", pnr);

			return resp.writeString(Observable.just(pnr2Address.get(pnr).toString()).delay(500, TimeUnit.MILLISECONDS));

		}).awaitShutdown();
	}

	private static String getRequestParamAsString(HttpServerRequest<ByteBuf> req, String name) {
		final List<String> params = req.getQueryParameters().get(name);
		return params.get(0);
	}
}
