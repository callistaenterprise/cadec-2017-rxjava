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

		//Get book from BookService

		//Get Address from PersonnummerService

		//Get Coordinates from CoordinateService and zip it with the book

		//Send the order using the bookOrderService
	}
}





















