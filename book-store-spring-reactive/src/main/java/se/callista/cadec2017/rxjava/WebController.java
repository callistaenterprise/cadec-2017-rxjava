package se.callista.cadec2017.rxjava;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

import java.util.concurrent.TimeUnit;

@RestController
public class WebController {
	@GetMapping("/hello")
	public Observable<String> hello() {

		 return Observable.interval(1, TimeUnit.SECONDS)
				 .zipWith(Observable.just("Hello", " ", "World", "!", "!"), (tick, s) -> s);
	}
}
