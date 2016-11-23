package se.callista.rxjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.callista.rxjava.services.TruckPositionService;

public class Application {

	private TruckPositionService truckPositionService = new TruckPositionService();
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String... args) throws Exception {
	    Application application = new Application();

		logger.debug("*****************************************************************");
		logger.debug("*********************** NEW SUBSCRIBER 1 *************************");
		logger.debug("*****************************************************************");

		application.truckPositionService.getTruckPositionStream().subscribe(p -> {
			logger.debug("{}: {}", Thread.currentThread().getName(), p);
		});

		Thread.sleep(5000);

		logger.debug("*****************************************************************");
		logger.debug("*********************** NEW SUBSCRIBER 2 *************************");
		logger.debug("*****************************************************************");

		application.truckPositionService.getTruckPositionStream().toBlocking().subscribe(p -> {
			logger.debug("{}: {}", Thread.currentThread().getName(), p);
		});
    }
}
