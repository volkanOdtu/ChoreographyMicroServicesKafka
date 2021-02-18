package com.techrocking.inventory.kafka.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.techrocking.inventory.kafka.channel.InventoryChannel;
import com.techrocking.inventory.kafka.message.ItemEvent;

@Component
public class itemOutOfStockEventSource {
	@Autowired
	private InventoryChannel inventoryChannel;
	
	public void publishItemOutOfStockEvent(Long orderId,Long itemId) {
		ItemEvent message = new ItemEvent();
		message.setItemId(itemId);
		message.setOrderId(orderId);
		message.setAction(ItemEvent.Action.ITEMOUTOFSTOCK);
		
		MessageChannel messageChannel =  inventoryChannel.outboundInventory();
		messageChannel.send(MessageBuilder.withPayload(message)
						.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());	
					
		System.out.println("inventory-service: is writing the topic(inventory-out) that the order-service is listening with teh action:ITEMOUTOFSTOCK"  );
		
	}
}
