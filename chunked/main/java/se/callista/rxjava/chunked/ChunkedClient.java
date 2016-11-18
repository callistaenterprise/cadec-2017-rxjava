package se.callista.rxjava.chunked;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientPipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import rx.Observable;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class ChunkedClient {
    public static void main(String[] args) {

        PipelineConfigurator<HttpClientResponse<ByteBuf>, HttpClientRequest<ByteBuf>> configurator =
                new HttpClientPipelineConfigurator<>();

        HttpClient<ByteBuf, ByteBuf> client = RxNetty.createHttpClient("localhost", 8080, configurator);

        Observable<String> stream = client.submit(HttpClientRequest.createGet("/chunkedResponse"))
                .flatMap(response ->
                        response.getContent().map(content ->
                    content.toString(Charset.defaultCharset())
                ));

        Observable<Long> clock = Observable.interval(0, 1, TimeUnit.SECONDS);

        stream
                .zipWith(clock, (a,b) -> a)
                .toBlocking()
                .last();
    }
}
