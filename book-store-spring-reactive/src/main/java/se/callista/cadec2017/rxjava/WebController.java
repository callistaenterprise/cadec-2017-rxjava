package se.callista.cadec2017.rxjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;
import se.callista.cadec2017.rxjava.model.Address;
import se.callista.cadec2017.rxjava.model.Book;
import se.callista.cadec2017.rxjava.model.Order;
import se.callista.cadec2017.rxjava.services.BooksService;
import se.callista.cadec2017.rxjava.services.CoordinateService;
import se.callista.cadec2017.rxjava.services.PersonnummerService;

@RestController
public class WebController {

	private BooksService booksService;
	private PersonnummerService personnummerService;
	private CoordinateService coordinateService;

	@Autowired
	public WebController(BooksService booksService, PersonnummerService personnummerService, CoordinateService coordinateService) {
		this.booksService = booksService;
		this.personnummerService = personnummerService;
		this.coordinateService = coordinateService;
	}

	@GetMapping("/placeOrder")
	public Single<Order> placeOrder(@RequestParam("title") String title, @RequestParam("author") String author, @RequestParam("pnr") String pnr) {
		final Single<Book> bookSingle = booksService.getBook(title, author);
		final Single<Address> addressSingle = personnummerService.getAddressByPersonnummer(pnr);

		return addressSingle.flatMap(address ->
				coordinateService.getCoordinateBroken(address)
						.onErrorResumeNext(coordinateService.getCoordinate(address))
						.zipWith(bookSingle, (coord, book) -> new Order(book, address, coord))
		);
	}
}
