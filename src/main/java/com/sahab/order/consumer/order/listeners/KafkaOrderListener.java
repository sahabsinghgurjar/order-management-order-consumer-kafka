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
import com.sahab.order.consumer.order.services.OrderDetailsService;

@Component
public class KafkaOrderListener {
	@Autowired
	private OrderDetailsService orderDetailsService;
	Logger LOG= LoggerFactory.getLogger(KafkaOrderListener.class);


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
	  }
	  

}
