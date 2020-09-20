package com.sahab.order.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import com.sahab.order.common.model.OrderDetails;
import com.sahab.order.consumer.processors.OrderProcessor;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamConfig {

	@Value("${kafka.topic.input}")
	private String inputTopic;

	@Value("${kafka.topic.output}")
	private String outputTopic;


	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kStreamsConfigs(KafkaProperties kafkaProperties) {
		Map<String, Object> config = new HashMap<>();
		config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CustomValueSerDe.class);
		return new KafkaStreamsConfiguration(config);
	}

	@Bean
	public KStream<String, OrderDetails> kStream(StreamsBuilder kStreamBuilder) {
		KStream<String, OrderDetails> stream = kStreamBuilder.stream(inputTopic);
		stream.process(new ProcessorSupplier<String, OrderDetails>() {

			@Override
			public Processor<String, OrderDetails> get() {
				return new OrderProcessor();
			}
		});
		stream.to(outputTopic);
		return stream;
	}

}
