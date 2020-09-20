package com.sahab.order.consumer.config;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahab.order.common.model.OrderDetails;

public class CustomValueSerDe implements Serde<OrderDetails>{
	ObjectMapper objectMapper=new ObjectMapper();

	@Override
	public Serializer<OrderDetails> serializer() {
		return new Serializer<OrderDetails>() {

			@Override
			public byte[] serialize(String topic, OrderDetails data) {
				try {
					return objectMapper.writeValueAsBytes(data);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return	new byte[] {};
			}
			
		};
	}

	@Override
	public Deserializer<OrderDetails> deserializer() {
		return new Deserializer<OrderDetails>() {

			@Override
			public OrderDetails deserialize(String topic, byte[] data) {
				try {
					return objectMapper.readValue(data, OrderDetails.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
	}

}
