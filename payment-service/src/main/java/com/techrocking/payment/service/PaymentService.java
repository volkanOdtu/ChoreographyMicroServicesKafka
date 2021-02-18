package com.techrocking.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techrocking.payment.kafka.source.PaymentFailedSource;
import com.techrocking.payment.kafka.source.PaymentReceivedSource;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentReceivedSource paymentReceivedSource;
	
	@Autowired
	private PaymentFailedSource paymentFailedSource;
	
	public void makePayment(Long orderId) {
		
		if(isPaymentSuccess()) {
			paymentReceivedSource.publishPaymentSuccessEvent(orderId, getPaymentId());
		}
		else {
			paymentFailedSource.publishPaymentFailedEvent(orderId);
		}
	}
	//This can be any payment gateway interface call
	private boolean isPaymentSuccess() {
		return true;
	}
	
	private Long getPaymentId() {
		return 6754L;
	}
}
