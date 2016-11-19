package se.callista.rxjava.services;


import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientPipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import rx.Observable;

import java.nio.charset.Charset;

public class LogService {

	public Observable<String> getLogStream(int throttleStream) {
		PipelineConfigurator<HttpClientResponse<ByteBuf>, HttpClientRequest<ByteBuf>> configurator = new HttpClientPipelineConfigurator<>();

		HttpClient<ByteBuf, ByteBuf> client = RxNetty.createHttpClient("localhost", 8090, configurator);

		return client.submit(HttpClientRequest.createGet("/logstream?throttle=" + throttleStream))
				.flatMap(response ->
						response.getContent().map(content ->
								content.toString(Charset.defaultCharset())
						));

	}
}
