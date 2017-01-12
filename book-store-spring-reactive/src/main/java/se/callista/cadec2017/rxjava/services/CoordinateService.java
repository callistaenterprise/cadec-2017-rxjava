package se.callista.cadec2017.rxjava.services;

import org.springframework.stereotype.Service;
import rx.Single;
import se.callista.cadec2017.rxjava.model.Address;
import se.callista.cadec2017.rxjava.model.Coordinates;

@Service
public class CoordinateService {

	public Single<Coordinates> getCoordinate(Address address) {
		return Single.just(new Coordinates("57.703062", "11.992746", 25));
	}

	public Single<Coordinates> getCoordinateBroken(Address address) {
		return Single.error(new RuntimeException());
	}

}
