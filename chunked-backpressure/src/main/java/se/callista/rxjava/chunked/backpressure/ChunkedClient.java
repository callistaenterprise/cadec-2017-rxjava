package se.callista.rxjava.chunked.backpressure;

import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class ChunkedClient {

    static Logger logger = LoggerFactory.getLogger(ChunkedClient.class);

    static long num = 0;

    public static void main(String[] args) {

        Observable<String> stream = HttpClient.newClient("127.0.0.1", 8080)
                .enableWireLogging("streaming-client", LogLevel.TRACE)
                .createGet("/stream")
                .doOnNext(resp -> logger.info(resp.toString()))
                .flatMap((HttpClientResponse<ByteBuf> resp) ->
                        resp.getContent()
                                .map(bb -> bb.toString(Charset.defaultCharset()))
                );


        Observable<Long> clock = Observable.interval(0, 1, TimeUnit.SECONDS);

        clock.zipWith(stream, (a,b) -> b).take(100000)
                .toBlocking()
                .forEach(ChunkedClient::log);
    }

    private static void log(String s) {
        logger.debug("{}",num++);
    }

}
