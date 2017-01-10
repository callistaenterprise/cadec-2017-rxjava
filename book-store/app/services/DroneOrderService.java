package services;

import io.reactivex.Single;
import model.Address;
import model.Book;
import model.Order;
import model.OrderForm;

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
		final Single<Book> bookSingle = booksService.getBook(orderForm.getBookTitle(), orderForm.getBookAuthor());
		final Single<Address> addressSingle = personnummerService.getAddressByPersonnummer(orderForm.getPnr());

		return addressSingle.flatMap(address ->
				coordinateService.getCoordinateBroken(address)
						.onErrorResumeNext(coordinateService.getCoordinate(address))
						.zipWith(bookSingle, (coord, book) -> new Order(book, address, coord))
						.flatMap(order -> bookOrderService.sendOrder(order))
		);
	}
}
