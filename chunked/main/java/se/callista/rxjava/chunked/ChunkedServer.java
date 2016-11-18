package se.callista.rxjava.chunked;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;
import se.parwen.rxjava.MyIterator;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ChunkedServer {
    public static void main(String[] args) throws InterruptedException {
        HttpServer<ByteBuf, ByteBuf> httpServer = RxNetty.createHttpServer(8080, (request, response) -> {
            Random random = new Random();
            return Observable.from(MyIterator::new)
                    .map(i -> String.format("%d\n", random.nextInt()))
                    .doOnNext(System.out::print)
                    .flatMap(response::writeStringAndFlush);
        });

        httpServer.startAndWait();
    }
}
