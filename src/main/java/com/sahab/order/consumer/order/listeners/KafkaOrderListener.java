package com.sahab.order.consumer.order.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sahab.order.consumer.order.services.OrderDetailsService;

@Component
public class KafkaOrderListener {
	@Autowired
	private OrderDetailsService orderDetailsService;
	Logger LOG= LoggerFactory.getLogger(KafkaOrderListener.class);
	

	  @KafkaListener(
	    topics = {"order-in-topic","test"}, 
	    groupId = "orderConsumerGroup",
	    containerFactory="rawDataContainerFactory")
	  void commonListenerForMultipleTopics(String message) {
		  System.out.println(message);
	  }
	

}
