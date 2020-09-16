package com.sahab.order.consumer.order.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.sahab.order.common.model.OrderDetails;
import com.sahab.order.consumer.exceptions.DuplicateOrderException;
import com.sahab.order.consumer.order.model.OrderDetailsVO;
import com.sahab.order.consumer.order.services.OrderDetailsService;

@Component
public class KafkaOrderListener {
	@Autowired
	private OrderDetailsService orderDetailsService;
	Logger LOG= LoggerFactory.getLogger(KafkaOrderListener.class);
	@KafkaListener(topics = "order-in-topic")
	  void listener(OrderDetails data) throws DuplicateOrderException {
		OrderDetailsVO orderDetailsVO=new OrderDetailsVO();
		orderDetailsVO.setOrderId(data.getOrderId());
		orderDetailsVO.setOrderName(data.getOrderName());
		orderDetailsVO.setPrice(data.getPrice());
		orderDetailsService.createOrder(orderDetailsVO);
		  System.out.println(data);
	  }

	 /* @KafkaListener(
	    topics = "order-in-topic,test", 
	    groupId = "orderConsumerGroup")
	  void commonListenerForMultipleTopics(String message) {
	   // LOG.info("MultipleTopicListener - {}", message);
		  System.out.println(message);
	  }*/
	
/*
	  @KafkaListener(
	    groupId = "orderConsumerGroup",
	    topicPartitions = @TopicPartition(
	      topic = "order-in-topic",
	      partitionOffsets = { @PartitionOffset(
	        partition = "0", 
	        initialOffset = "0") }))
	  void listenToPartitionWithOffset(
	    @Payload OrderDetails message,
	    @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
	    @Header(KafkaHeaders.OFFSET) int offset) {
		  LOG.info("Received message [{}] from partition-{} with offset-{}", 
	        message, 
	        partition, 
	        offset);
	  }*/
	  

	 /* @KafkaListener(topics = "order-in-topic")
	  @SendTo("order-out-topic")
	  String listenAndReply(String message) {
	    LOG.info("ListenAndReply [{}]", message);
	    return "This is a reply sent after receiving message";
	  }*/
	  
	 // in case of multiple listeners , we can specifying which container factory to use.
	  
	  /*@KafkaListener(
			    topics = "order-in-topic",
			    groupId="orderConsumerGroup",
			    containerFactory="userKafkaListenerContainerFactory")
			  void listener(User user) {
			    LOG.info("CustomUserListener [{}]", user);
			  }*/
}
