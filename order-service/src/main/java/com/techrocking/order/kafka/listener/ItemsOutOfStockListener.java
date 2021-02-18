package com.techrocking.order.kafka.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.techrocking.order.kafka.channel.OrderChannel;
import com.techrocking.order.kafka.message.ItemEvent;
import com.techrocking.order.service.OrderService;

@Component
public class ItemsOutOfStockListener {
	@Autowired
	private OrderService orderService;
	
	//Burda inventory service channel indan Kafka topic ine gelen objeyi dinliyoruz
	//message.setAction(ItemEvent.Action.ITEMOUTOFSTOCK) olarak bu channel a gonderdi Inventory service 
	//Biz de order i db den dusucez ve notification-service in Kafka daki topic ine message gondericez	
	@StreamListener(OrderChannel.INPUT)
	public void listenOutOfStockItem(@Payload ItemEvent itemEvent) {
		System.out.println("order-service: listenOutOfStockItem is accessed");
		
		if(itemEvent.getAction().equals(ItemEvent.Action.ITEMOUTOFSTOCK)) {
			System.out.println("Order service: Item out of stock for the item: " + itemEvent.getItemId());
			System.out.println("Order service: Going to compensate order for id: " + itemEvent.getOrderId());
			orderService.compensateOrder(itemEvent.getOrderId());
		}
	}
}
