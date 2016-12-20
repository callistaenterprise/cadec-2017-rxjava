package services;

import com.fasterxml.jackson.databind.JsonNode;
import io.reactivex.Single;
import model.Book;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import javax.inject.Inject;

import static utils.FutureUtils.toSingle;

public class BooksService {

	@Inject WSClient ws;

	public Single<Book> getBook(String title, String author) {
		System.out.println("title: " + title + ", author: " + author);
		return toSingle(
				ws.url("http://192.168.99.100:9081/books")
						.setQueryParameter("title", title)
						.setQueryParameter("author", author)
						.get())
				.map(WSResponse::asJson)
				.map(json -> Json.fromJson(json.get(0), Book.class));
	}
}
