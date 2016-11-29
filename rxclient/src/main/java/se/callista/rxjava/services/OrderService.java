package se.callista.rxjava.services;

import org.springframework.stereotype.Service;
import se.callista.rxjava.Coordinate;
import se.callista.rxjava.Order;

@Service
public class OrderService {

	public Coordinate placeOrder(Order order) {
		return new Coordinate(57.649480, 12.016571);
	}

}
