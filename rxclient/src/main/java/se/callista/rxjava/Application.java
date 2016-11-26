package se.callista.rxjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.callista.rxjava.services.TruckPositionService;

public class Application {

	private TruckPositionService truckPositionService = new TruckPositionService();
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String... args) throws Exception {
	    Application application = new Application();

		application.truckPositionService.getTruckPositionStream().toBlocking().subscribe(p ->
			logger.debug("{}: {}", Thread.currentThread().getName(), p)
		);
    }
}
