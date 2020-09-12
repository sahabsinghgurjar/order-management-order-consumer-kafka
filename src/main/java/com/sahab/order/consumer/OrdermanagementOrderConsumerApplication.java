package com.sahab.order.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:secrets.properties")
public class OrdermanagementOrderConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdermanagementOrderConsumerApplication.class, args);
	}

}
	