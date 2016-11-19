package se.callista.rxjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.callista.rxjava.services.LogService;

import static java.util.concurrent.TimeUnit.SECONDS;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private LogService logService;

	@Override
    public void run(String... args) throws Exception {
		System.out.println("args = " + args);
		logService.getLogStream().sample(1, SECONDS).toBlocking().subscribe(System.out::print);
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
