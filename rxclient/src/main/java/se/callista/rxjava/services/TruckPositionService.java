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

public class TruckPositionService {

	public Observable<String> getTruckPositionStream() {
		PipelineConfigurator<HttpClientResponse<ByteBuf>, HttpClientRequest<ByteBuf>> configurator = new HttpClientPipelineConfigurator<>();

		HttpClient<ByteBuf, ByteBuf> client = RxNetty.createHttpClient("localhost", 8070, configurator);

		return client.submit(HttpClientRequest.createGet("/waypointstream?speed=50&truckId=1&lat=57.484345&lng=12.008857"))
				.mergeWith(client.submit(HttpClientRequest.createGet("/waypointstream?speed=50&truckId=2&lat=57.484345&lng=12.008857")))
				.flatMap(response ->
						response.getContent().map(content ->
								content.toString(Charset.defaultCharset())
						));

	}

}
