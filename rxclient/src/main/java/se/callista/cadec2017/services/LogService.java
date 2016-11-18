package se.callista.cadec2017.services;


import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientPipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.nio.charset.Charset;

@Service
public class LogService {

	@Value("${log.throttle:1}")
	private int throttle;

	public Observable<String> getLogStream() {
		PipelineConfigurator<HttpClientResponse<ByteBuf>, HttpClientRequest<ByteBuf>> configurator = new HttpClientPipelineConfigurator<>();

		HttpClient<ByteBuf, ByteBuf> client = RxNetty.createHttpClient("localhost", 8080, configurator);

		return client.submit(HttpClientRequest.createGet("/chunkedResponse?throttle=" + throttle))
				.flatMap(response ->
						response.getContent().map(content ->
								content.toString(Charset.defaultCharset())
						));

	}
}
