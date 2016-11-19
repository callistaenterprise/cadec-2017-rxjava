package se.callista.rxjava;

import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.server.HttpServer;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

public class LogStreamServer {

	static Logger logger = LoggerFactory.getLogger(LogStreamServer.class);

	private static final int DEFAULT_THROTTLE = 1;
	private static final int PORT = 8090;

	public static void main(String[] args) throws IOException {
		InputStream fileStream = LogStreamServer.class.getResourceAsStream("NASA_access_log_Jul95.gz");
		InputStream gzipStream = new GZIPInputStream(fileStream);
		Reader decoder = new InputStreamReader(gzipStream, "UTF-8");
		BufferedReader logfile = new BufferedReader(decoder);

		HttpServer<ByteBuf, ByteBuf> server;

		server = HttpServer.newServer(PORT)
				.enableWireLogging("streaming-server", LogLevel.TRACE)
				.start((req, resp) -> {

					final List<String> throttleParam = req.getQueryParameters().get("throttle");
					int throttle = DEFAULT_THROTTLE;

					if (CollectionUtils.isNotEmpty(throttleParam)) {
						throttle = Integer.parseInt(throttleParam.get(0));
					}

					final Observable<String> logStream = Observable.from(() -> logfile.lines().iterator());

					return resp.writeStringAndFlushOnEach(
							Observable.interval(throttle, TimeUnit.MILLISECONDS)
									.onBackpressureBuffer(10)
									.zipWith(logStream, (tick, logrow) -> logrow + "\n")
									.doOnNext(LogStreamServer::log)
					);
				});

		server.awaitShutdown();
	}

	private static void log(String s) {
		logger.debug(s.substring(0, s.indexOf(':')));
	}
}
