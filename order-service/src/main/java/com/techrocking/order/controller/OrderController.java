package com.techrocking.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techrocking.order.payload.PlaceOrderRequest;
import com.techrocking.order.payload.PlaceOrderResponse;
import com.techrocking.order.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping()
	public PlaceOrderResponse placeOrder(@RequestParam("itemId") long itemId ,@RequestParam("customerId") long customerId  /*PlaceOrderRequest request*/) {
		
		PlaceOrderRequest request = new PlaceOrderRequest();
		
		request.setItemId(itemId);
		request.setCustomerId(customerId);
		
		return orderService.createOrder(request);
	}
}
