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

		return Single.just(new Order(null, null, null));

		//1. Get book from BookService

		//2. Get Address from PersonnummerService

		//3. Get Coordinates from CoordinateService and zip it with the book

		//4. Send the order using the bookOrderService
	}
}





















