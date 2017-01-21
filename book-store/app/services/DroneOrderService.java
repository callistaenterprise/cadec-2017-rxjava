package services;

import io.reactivex.Single;
import model.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DroneOrderService {
	private BooksService booksService;
	private PersonnummerService personnummerService;
	private CoordinateService coordinateService;
	private BookOrderService bookOrderService;

	@Inject
	public DroneOrderService(BooksService booksService, PersonnummerService personnummerService, CoordinateService coordinateService, BookOrderService bookOrderService) {
		this.booksService = booksService;
		this.personnummerService = personnummerService;
		this.coordinateService = coordinateService;
		this.bookOrderService = bookOrderService;
	}





	public Single<Order> placeDroneOrder(OrderForm orderForm) {
		//Get book from BookService
		final Single<Book> bookSingle = booksService.getBook(orderForm.getBookTitle(), orderForm.getBookAuthor());

		//Get Address from AddressService
		final Single<Address> addressSingle = personnummerService.getAddressByPersonnummer(orderForm.getPnr());

		//Get Coordinates from CoordinateService and zip it with the book
		final Single<Order> orderSingle = addressSingle
				.flatMap(address -> coordinateService.getCoordinateBroken(address)
						.onErrorResumeNext(coordinateService.getCoordinate(address))
						.zipWith(bookSingle, ((coordinates, book) -> new Order(book, address, coordinates)))
				);

		//Send the order using the bookOrderService
		return orderSingle.flatMap(order -> bookOrderService.sendOrder(order));

	}
}





















