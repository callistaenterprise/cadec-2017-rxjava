package se.callista.rxjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import se.callista.rxjava.services.TruckPositionService;

@SpringBootApplication
public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String... args) throws Exception {
		SpringApplication.run(Application.class, args);

//		application.truckPositionService.getTruckPositionStream().toBlocking().subscribe(p ->
//			logger.debug("{}: {}", Thread.currentThread().getName(), p)
//		);
    }
}
