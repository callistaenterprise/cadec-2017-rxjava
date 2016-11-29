package se.callista.rxjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import se.callista.rxjava.Coordinate;
import se.callista.rxjava.Order;
import se.callista.rxjava.services.OrderService;

import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class OrderController {

	private OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@RequestMapping(method = PUT, path = "/")
	public ResponseEntity<Void> putOrder(@RequestBody Order order) {
		Coordinate coordinate = orderService.placeOrder(order);
		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(createDroneStreamLocationUri(coordinate));
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	private URI createDroneStreamLocationUri(Coordinate coordinate) {
		final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost:8070/");
		return uriComponentsBuilder
				.path("droneStream")
				.queryParam("lat", coordinate.getLatitude())
				.queryParam("long", coordinate.getLongitude())
				.buildAndExpand().toUri();
	}


}
