package com.techrocking.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.techrocking.shipping.kafka.channel.ShippingChannel;

@SpringBootApplication
@EnableBinding(ShippingChannel.class)
public class ShippingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingServiceApplication.class, args);
	}

}
