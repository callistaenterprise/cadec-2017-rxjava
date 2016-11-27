package se.callista.rxjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.callista.rxjava.services.TruckPositionService;

@RestController
public class OrderController {

	private TruckPositionService truckPositionService;

	@Autowired
	public OrderController(TruckPositionService truckPositionService) {
		this.truckPositionService = truckPositionService;
	}

	@RequestMapping("/")
	public String getOrder() {
		return truckPositionService.toString();
	}

}
