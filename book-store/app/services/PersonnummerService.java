package services;

import io.reactivex.Single;
import model.Address;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import static utils.FutureUtils.toSingle;

@Singleton
public class PersonnummerService {

	@Inject WSClient ws;

	public Single<Address> getAddressByPersonnummer(String personnumer) {
		return toSingle(ws.url("http://192.168.99.100:9082/" + personnumer).get())
				.map(WSResponse::asJson)
				.map(json -> Json.fromJson(json, Address.class));
	}

}
