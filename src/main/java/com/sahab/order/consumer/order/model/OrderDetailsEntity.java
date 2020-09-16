package com.sahab.order.consumer.order.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="orderdetails")
@Data
public class OrderDetailsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="order_name")
	private String orderName;
	@Column(name="price")
	private Double price;
	@Id
	@Column(name="order_id")
	private String orderId;


}
