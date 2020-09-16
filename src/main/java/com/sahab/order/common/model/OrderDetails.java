package com.sahab.order.common.model;

import lombok.Data;

@Data
public class OrderDetails {
	private String orderName;
	private Double price;
	private String orderId;

}
