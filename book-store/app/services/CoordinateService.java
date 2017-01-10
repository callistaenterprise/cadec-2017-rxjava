package services;

import io.reactivex.Single;
import model.Address;
import model.Coordinates;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static utils.FutureUtils.toSingle;

@Singleton
public class CoordinateService {

	@Inject WSClient ws;

	public Single<Coordinates> getCoordinate(Address address) {
		System.out.println("Get Coordinates for: " + address.city);

		return toSingle(ws.url("http://192.168.99.100:9080/" + urlEncode(address.city)).get())
				.map(WSResponse::asJson)
				.map(json -> Json.fromJson(json, Coordinates.class));
	}

	public Single<Coordinates> getCoordinateBroken(Address address) {
		return toSingle(ws.url("http://192.168.99.100:9089/").get())
				.map(WSResponse::asJson)
				.map(json -> Json.fromJson(json, Coordinates.class));

	}

	private static String urlEncode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
