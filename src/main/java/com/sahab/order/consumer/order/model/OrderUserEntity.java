package com.sahab.order.consumer.order.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="orderuser")
@Data
public class OrderUserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="FirstName")
	private String firstName;
	@Column(name="LastName")
	private String lastName;
	@Column(name="Email")
	private String email;
	@Column(name="Phone")
	private int phone;
	
	@Column(name="Address")
	private String address;
	
	@Id
	@Column(name="UserId")
	private String userId;

}
