package se.callista.cadec2017;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.callista.cadec2017.services.LogService;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private LogService logService;

	@Override
    public void run(String... args) throws Exception {
        logService.getLogStream().sample(1, SECONDS).toBlocking().subscribe(System.out::print);
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
