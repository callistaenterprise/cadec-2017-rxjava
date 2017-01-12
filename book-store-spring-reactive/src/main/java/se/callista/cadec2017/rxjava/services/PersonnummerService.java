package se.callista.cadec2017.rxjava.services;


import org.springframework.stereotype.Service;
import rx.Single;
import se.callista.cadec2017.rxjava.model.Address;

@Service
public class PersonnummerService {

	public Single<Address> getAddressByPersonnummer(String personnumer) {
		return Single.just(new Address("Anders Asplund", "Fabriksgatan 25", "Goteborg"));
	}

}
