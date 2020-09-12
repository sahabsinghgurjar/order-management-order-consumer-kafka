package com.sahab.order.consumer.order.model;

import lombok.Data;

@Data
public class OrderUser {
	private String firstName;
	private String lastName;
	private String address;
	private int phone;
	private String userId;
	private String email;

}
