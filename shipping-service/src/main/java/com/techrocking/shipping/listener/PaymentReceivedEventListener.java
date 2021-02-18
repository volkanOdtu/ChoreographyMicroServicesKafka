package com.techrocking.shipping.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.techrocking.shipping.kafka.channel.ShippingChannel;
import com.techrocking.shipping.kafka.message.PaymentEvent;
import com.techrocking.shipping.service.ShippingService;

@Component
public class PaymentReceivedEventListener {
	@Autowired
	private ShippingService shippingService;
	
	@StreamListener(ShippingChannel.INPUT)
	public void listenItemFetchedEvent(@Payload PaymentEvent paymentReceivedMessage) {
		System.out.println("shipping-service: listenItemFetchedEvent is accessed ");
		
		if(PaymentEvent.PaymentAction.PAYMENTRECEIVED.equals(paymentReceivedMessage.getAction())) {
			
			if(paymentReceivedMessage.getOrderId()!= null) {
				System.out.println("shipping service: shipment is started for order: " + paymentReceivedMessage.getOrderId());
				shippingService.processShippment(paymentReceivedMessage.getOrderId());
			}
		}
	}
}
