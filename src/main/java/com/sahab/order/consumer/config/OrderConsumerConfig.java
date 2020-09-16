package com.sahab.order.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.sahab.order.common.model.OrderDetails;


@Configuration
@EnableKafka
public class OrderConsumerConfig {

	@Value("${bootstrap-servers}")
	  private String bootstrapServers;
	
	@Value("${consumer-groupId}")
	  private String consumerGroupId;

	  @Bean
	  public Map<String, Object> consumerConfigs() {
	    Map<String, Object> props = new HashMap<>();
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
	      bootstrapServers);
	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
	      StringDeserializer.class);
	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
	    		JsonDeserializer.class);
	    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
	    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
	    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
	    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10000);
	    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	    return props;
	  }

	  @Bean
	  public ConsumerFactory<String, OrderDetails> consumerFactory() {
	    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	  }

	  @Bean
	  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderDetails>> kafkaListenerContainerFactory() {
	    ConcurrentKafkaListenerContainerFactory<String, OrderDetails> factory =
	      new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(consumerFactory());
	    factory.setRecordFilterStrategy(record -> 
	      record.value().getOrderName().contains("ignored"));
	    return factory;
	  }
	  
	/*  public ConsumerFactory<String, User> userConsumerFactory() {
		    Map<String, Object> props = new HashMap<>();
		    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		    props.put(ConsumerConfig.GROUP_ID_CONFIG, "reflectoring-user");

		    return new DefaultKafkaConsumerFactory<>(
		      props,
		      new StringDeserializer(),
		      new JsonDeserializer<>(User.class));
		  }
	  */
	  
	  
}
