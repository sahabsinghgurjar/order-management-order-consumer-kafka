package com.sahab.order.consumer.order.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderListener {
	Logger LOG= LoggerFactory.getLogger(KafkaOrderListener.class);
	@KafkaListener(topics = "order-in-topic")
	  void listener(String data) {
	    //LOG.info(data);
		  System.out.println(data);
	  }

	 /* @KafkaListener(
	    topics = "order-in-topic,test", 
	    groupId = "orderConsumerGroup")
	  void commonListenerForMultipleTopics(String message) {
	   // LOG.info("MultipleTopicListener - {}", message);
		  System.out.println(message);
	  }*/
	

	  @KafkaListener(
	    groupId = "orderConsumerGroup",
	    topicPartitions = @TopicPartition(
	      topic = "order-in-topic",
	      partitionOffsets = { @PartitionOffset(
	        partition = "0", 
	        initialOffset = "0") }))
	  void listenToPartitionWithOffset(
	    @Payload String message,
	    @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
	    @Header(KafkaHeaders.OFFSET) int offset) {
		  LOG.info("Received message [{}] from partition-{} with offset-{}", 
	        message, 
	        partition, 
	        offset);
	  }
	  

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
