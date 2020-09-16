package com.sahab.order.consumer.order.model;

import lombok.Data;

@Data
public class OrderDetailsVO {
	private String orderName;
	private Double price;
	private String orderId;

}
