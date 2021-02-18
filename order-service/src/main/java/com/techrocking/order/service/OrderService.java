package com.techrocking.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techrocking.order.entity.Order;
import com.techrocking.order.kafka.source.OrderNotProcessedEventSource;
import com.techrocking.order.kafka.source.OrderPlacedEventSource;
import com.techrocking.order.payload.PlaceOrderRequest;
import com.techrocking.order.payload.PlaceOrderResponse;

@Service
public class OrderService {

	@Autowired
	private OrderPlacedEventSource orderPlacedEventSource;
	
	@Autowired
	private OrderNotProcessedEventSource orderNotProcessedEventSource;
	
	public PlaceOrderResponse createOrder(PlaceOrderRequest request) {
		Order order = new Order();
		
		order.setItemName("item-xyz");
		order.setCustomerName("customer-abc");
		order.setId(234L);
		
		//orderPlacedEventSource Kafka ya message channel acip ,obje gonderen bi class
		orderPlacedEventSource.publishOrderEvent(order.getId());
		PlaceOrderResponse response = new PlaceOrderResponse();
		
		response.setMessage("order placed successfully");
		response.setOrderId(order.getId());
		
		return response;
	}
	
	public void compensateOrder(Long orderId) {
		//delete the order from db
		//Bu islemi  yukaridakinin tersi olarak yaptigimizi varsayalim
		System.out.println("order removed from db:" + orderId); 
		//orderNotProcessedEventSource Kafka ya message channel aciyor ,objenin action enum: orderNotPlaced 
		orderNotProcessedEventSource.publishOrderNotProcessedEvent(orderId);
	}
}
