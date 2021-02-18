package com.techrocking.inventory.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.techrocking.inventory.kafka.channel.InventoryChannel;
import com.techrocking.inventory.kafka.message.OrderEvent;
import com.techrocking.inventory.service.InventoryService;

@Component
public class OrderPlacedEventListener {
	
	@Autowired
	private InventoryService inventoryService; 
	
	//Burda order-service microservice inin (shoppingCart-order-Topic) a gonderdigi mesaji okuyoruz
	@StreamListener(target = InventoryChannel.INPUT_ORDER)
	public void listenPaymentReceived(@Payload OrderEvent orderEvent) {
		System.out.println("inventory-service: listenPaymentReceived is accessed");
		
		if(orderEvent.getAction().equals(OrderEvent.OrderAction.ORDERPLACED)) {
			System.out.println("inventory-service: order placed action received ,payment service will be called");
			inventoryService.fetchItem(orderEvent.getOrderId());
		}
	}
}
