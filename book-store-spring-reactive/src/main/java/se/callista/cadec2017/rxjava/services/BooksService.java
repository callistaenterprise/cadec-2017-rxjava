package se.callista.cadec2017.rxjava.services;

import org.springframework.stereotype.Service;
import rx.Single;
import se.callista.cadec2017.rxjava.model.Book;

@Service
public class BooksService {

	public Single<Book> getBook(String title, String author) {
		return Single.just(new Book("Reactive Programming with RxJava",
				"Tomasz Nurkiewicz, Ben Christiansen, Erik Meijer", "1491931655", "978-1491931653"));
	}
}
