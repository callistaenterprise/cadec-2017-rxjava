package services;

import io.reactivex.Single;
import model.Order;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class BookOrderService {
	private Map<Integer, Order> orders = new HashMap<>();

	public Single<Order> sendOrder(Order order) {
		final Order savedOrder = order.setOrderId(orders.size() + 1);
		orders.put(savedOrder.getOrderId(), savedOrder);
		return Single.just(savedOrder);
	}

	public Single<Order> getOrder(int orderId) {
		return Single.just(orders.get(orderId));
	}

}
