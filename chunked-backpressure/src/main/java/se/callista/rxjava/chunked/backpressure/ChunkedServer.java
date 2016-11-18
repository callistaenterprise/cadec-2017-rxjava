package se.callista.rxjava.chunked.backpressure;

import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public class ChunkedServer {

    static Logger logger = LoggerFactory.getLogger(ChunkedServer.class);

    public static void main(String[] args) {

        HttpServer<ByteBuf, ByteBuf> server;

        String bigData = ":" + new String(new char[1024*1024]).replace('\0', 'x');

        server = HttpServer.newServer(8080)
                .enableWireLogging("streaming-server", LogLevel.TRACE)
                .start((req, resp) ->
                        resp.writeStringAndFlushOnEach(
                                Observable.interval(10, TimeUnit.MILLISECONDS)
                                        .onBackpressureBuffer(10)
                                        .map(aLong -> "Interval =>" + aLong + bigData + '\n')
                                        .doOnNext(ChunkedServer::log)
                        )
                );

        server.awaitShutdown();
    }

    private static void log(String s) {
        logger.debug(s.substring(0, s.indexOf(':')));
    }
}
