package se.callista.rxjava;

import se.callista.rxjava.services.TruckPositionService;

public class Application {

	private TruckPositionService truckPositionService = new TruckPositionService();

	public static void main(String... args) throws Exception {
	    Application application = new Application();
	    application.truckPositionService.getTruckPositionStream().toBlocking().subscribe(System.out::print);
    }
}
