package services;

import io.reactivex.Single;
import model.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DroneOrderService {
	private BooksService bookService;
	private PersonnummerService personnummerService;
	private CoordinateService coordinateService;
	private BookOrderService bookOrderService;

	@Inject
	public DroneOrderService(BooksService bookService, PersonnummerService personnummerService, CoordinateService coordinateService, BookOrderService bookOrderService) {
		this.bookService = bookService;
		this.personnummerService = personnummerService;
		this.coordinateService = coordinateService;
		this.bookOrderService = bookOrderService;
	}





	public Single<Order> placeDroneOrder(OrderForm orderForm) {

		//1. Get book from BookService
		final Single<Book> bookSingle = bookService.getBook(orderForm.getBookTitle(), orderForm.getBookAuthor());

		//2. Get Address from PersonnummerService
		final Single<Address> addressSingle = personnummerService.getAddressByPersonnummer(orderForm.getPnr());

		//3. Get Coordinates from CoordinateService and zip it with the book
		final Single<Order> orderSingle = addressSingle
				.flatMap(address -> coordinateService.getCoordinate(address)
						.zipWith(bookSingle, (coordinates, book) -> new Order(book, address, coordinates))
				);

		//4. Send the order using the bookOrderService
		return orderSingle.flatMap(order -> bookOrderService.sendOrder(order));

	}
}





















