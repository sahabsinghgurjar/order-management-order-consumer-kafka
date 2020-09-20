package com.sahab.order.consumer.processors;

import org.apache.kafka.streams.processor.AbstractProcessor;

import com.sahab.order.common.model.OrderDetails;

public class OrderProcessor extends AbstractProcessor<String,OrderDetails> {

	@Override
	public void process(String key, OrderDetails value) {
		System.out.println("Data got processed.."+value);
		
	}

}
