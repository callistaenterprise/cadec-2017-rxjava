package se.callista.rxjava.services;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientPipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.nio.charset.Charset;

@Service
public class TruckPositionService {

	public Observable<String> getTruckPositionStream() {
		PipelineConfigurator<HttpClientResponse<ByteBuf>, HttpClientRequest<ByteBuf>> configurator = new HttpClientPipelineConfigurator<>();

		HttpClient<ByteBuf, ByteBuf> client = RxNetty.createHttpClient("localhost", 8070, configurator);

		return client.submit(HttpClientRequest.createGet("/waypointstream?droneId=1"))
				.flatMap(response ->
						response.getContent().map(content ->
								content.toString(Charset.defaultCharset())
						));

	}

}
