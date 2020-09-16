package com.sahab.order.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

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
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10000);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return props;
	}

	@Bean
	public ConsumerFactory<String, String> rawDataconsumerFactory() {
		Map<String, Object> config = consumerConfigs();
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId+1);
		return new DefaultKafkaConsumerFactory<>(config);
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> rawDataContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(rawDataconsumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, OrderDetails> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderDetails>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, OrderDetails> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setReplyTemplate(kafkaReplyTemplate());
		factory.setRecordFilterStrategy(record -> record.value().getOrderName().contains("ignored"));
		return factory;
	}
	
/******************************************Reply template config********************/	

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put( 
		            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
		            StringSerializer.class); 
		return props;
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}
	@Bean
	  public KafkaTemplate<String, String> kafkaReplyTemplate() {
		  KafkaTemplate<String, String> kafkaTemplate= new KafkaTemplate<>(producerFactory());
		  return kafkaTemplate;
	  }

}
