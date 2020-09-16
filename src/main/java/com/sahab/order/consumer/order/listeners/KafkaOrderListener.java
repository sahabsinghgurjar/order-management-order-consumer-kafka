package com.sahab.order.consumer.order.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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
}
