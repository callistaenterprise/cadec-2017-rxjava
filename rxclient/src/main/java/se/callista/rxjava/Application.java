package se.callista.rxjava;

import se.callista.rxjava.services.LogService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Application {

	private LogService logService = new LogService();

    public static void main(String... args) throws Exception {
	    Application application = new Application();
//	    application.logService.getLogStream(100).sample(1, SECONDS).toBlocking().subscribe(System.out::print);
	    application.logService.getWaypointStream().toBlocking().subscribe(System.out::print);
    }


}
