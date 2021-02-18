package com.techrocking.payment.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.techrocking.payment.kafka.channel.PaymentChannel;
import com.techrocking.payment.kafka.message.ItemEvent;
import com.techrocking.payment.service.PaymentService;

@Component
public class ItemFetchedEventListener {
	@Autowired
	private PaymentService paymentService;
	
	@StreamListener(PaymentChannel.INPUT)
	public void listenItemFetchedEvent(@Payload ItemEvent itemFetchedMessage) {
		System.out.println("payment-service: listenItemFetchedEvent is reached now");
		
		if(ItemEvent.Action.ITEMFETCHED.equals(itemFetchedMessage.getAction())) {
			if(itemFetchedMessage.getOrderId() != null && itemFetchedMessage.getItemId() != null)
			{
				System.out.println("payment-service: item payment is being done now");
				paymentService.makePayment(itemFetchedMessage.getOrderId());
			}
		}
	}
}
