package controllers;

import io.reactivex.Single;
import model.Order;
import model.OrderForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.ExecutionContextExecutor;
import services.BookOrderService;
import services.BooksService;
import services.CoordinateService;
import services.PersonnummerService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static utils.FutureUtils.fromSingle;

@Singleton
public class BookStoreController extends Controller {
	private final HttpExecutionContext exec;
	private final PersonnummerService personnummerService;
	private final BooksService booksService;
	private final CoordinateService coordinateService;
	private final BookOrderService bookOrderService;
	private final Form<OrderForm> orderForm;

	@Inject
	public BookStoreController(PersonnummerService personnummerService, BooksService booksService,
	                           CoordinateService coordinateService, BookOrderService bookOrderService,
	                           HttpExecutionContext exec, FormFactory formFactory) {
		this.exec = exec;
		this.personnummerService = personnummerService;
		this.booksService = booksService;
		this.coordinateService = coordinateService;
		this.orderForm = formFactory.form(OrderForm.class);
		this.bookOrderService = bookOrderService;
	}

	public Result getOrderForm() {
		return ok(views.html.order.render(orderForm));
	}

	public CompletionStage<Result> showDelivery(int orderId) {
		final Single<Order> order = bookOrderService.getOrder(orderId);

		return fromSingle(order).thenApplyAsync(o -> ok(views.html.delivery.render(o)), exec.current());
	}

	public CompletionStage<Result> placeOrder() {
		Form<OrderForm> boundForm = orderForm.bindFromRequest();
		if (boundForm.hasErrors()) {
			return CompletableFuture.supplyAsync(() -> badRequest(views.html.order.render(boundForm)));
		}
		return fromSingle(placeDroneOrder(boundForm.get()))
				.thenApplyAsync(order -> ok(views.html.orderPlaced.render(order)), exec.current());
	}


	private Single<Order> placeDroneOrder(OrderForm orderForm) {

		return personnummerService.getAddressByPersonnummer(orderForm.getPnr())
				.zipWith(booksService.getBook(orderForm.getBookTitle(), orderForm.getBookAuthor()),
						(address, book) -> new Order(book, address))
				.flatMap(order ->
						coordinateService.getCoordinateBroken(order.getAddress())
								.onErrorResumeNext(coordinateService.getCoordinate(order.getAddress()))
								.map(order::setCoordinates))
				.flatMap(order -> bookOrderService.sendOrder(order));

	}
}
