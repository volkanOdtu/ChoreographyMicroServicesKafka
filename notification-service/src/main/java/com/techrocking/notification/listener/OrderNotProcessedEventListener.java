package com.techrocking.notification.listener;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.techrocking.notification.kafka.channel.NotificationChannel;
import com.techrocking.notification.kafka.message.OrderEvent;

@Component
public class OrderNotProcessedEventListener {

	//Burasi order-service microservice nin gonderdigi  
	@StreamListener(target = NotificationChannel.INPUT_ORDER)
	public void listenOrderNotProcessedEvent(@Payload OrderEvent message) {
		System.out.println("notification-service: listenOrderNotProcessedEvent is accessed");
		
		if (OrderEvent.OrderAction.ORDERNOTPLACED.equals(message.getAction())) {
			System.out.println("Notification service: order not placed: " + message.getOrderId());
		}
	}
}
