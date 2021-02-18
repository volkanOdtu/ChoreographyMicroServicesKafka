package com.techrocking.order.kafka.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.techrocking.order.kafka.channel.OrderChannel;
import com.techrocking.order.kafka.message.OrderEvent;

@Component
public class OrderNotProcessedEventSource {

	@Autowired
	private OrderChannel orderChannel;
	
	//Bu metod inventoryService in output channel ina OutofStock action i ile gonderilmisti
	//burda da biz notification service in dinledigi topic e yaziyoruz,payLoad olarak ORDERNOTPLACED gidiyor
	public void publishOrderNotProcessedEvent(Long orderId) {
		OrderEvent message = new OrderEvent();
		message.setOrderId(orderId);
		message.setAction(OrderEvent.OrderAction.ORDERNOTPLACED);
		
		MessageChannel messageChannel = orderChannel.outboundOrder();
		messageChannel.send(MessageBuilder.withPayload(message)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build());
		System.out.println("Order service: Order not placed: " + orderId);
	}
}
