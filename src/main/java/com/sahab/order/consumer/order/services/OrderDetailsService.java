package com.sahab.order.consumer.order.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sahab.order.consumer.exceptions.DuplicateOrderException;
import com.sahab.order.consumer.exceptions.OrderDoesNotExistException;
import com.sahab.order.consumer.order.model.OrderDetailsVO;
import com.sahab.order.consumer.order.model.OrderDetailsEntity;
import com.sahab.order.consumer.order.repo.OrderDetailsRepo;

@Service
public class OrderDetailsService {
	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	public List<OrderDetailsVO> getAllOrders() {
		return orderDetailsRepo.findAll().stream().map(orderDetailsEntity -> {
			OrderDetailsVO orderDetails = new OrderDetailsVO();
			orderDetails.setOrderId(orderDetailsEntity.getOrderId());
			orderDetails.setOrderName(orderDetailsEntity.getOrderName());
			orderDetails.setPrice(orderDetailsEntity.getPrice());
			return orderDetails;
		}).collect(Collectors.toList());
	}

	public void createOrder(OrderDetailsVO odrerDetails) throws DuplicateOrderException {

		if (orderDetailsRepo.findById(odrerDetails.getOrderId()).isPresent()) {
			throw new DuplicateOrderException();
		}
		OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();
		orderDetailsEntity.setOrderId(odrerDetails.getOrderId());
		orderDetailsEntity.setOrderName(odrerDetails.getOrderName());
		orderDetailsEntity.setPrice(odrerDetails.getPrice());
		
		orderDetailsRepo.save(orderDetailsEntity);

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateOrder(OrderDetailsVO orderDetails) throws OrderDoesNotExistException {
		Optional<OrderDetailsEntity> orderDetailsOptional = orderDetailsRepo.findById(orderDetails.getOrderId());

		if (!orderDetailsRepo.findById(orderDetails.getOrderId()).isPresent()) {
			throw new OrderDoesNotExistException();
		}
		OrderDetailsEntity orderDetailsEntity =orderDetailsOptional.get();
		orderDetailsEntity.setOrderId(orderDetails.getOrderId());
		orderDetailsEntity.setOrderName(orderDetails.getOrderName());
		orderDetailsEntity.setPrice(orderDetails.getPrice());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteOrder(String orderId) throws OrderDoesNotExistException {

		if (!orderDetailsRepo.findById(orderId).isPresent()) {
			throw new OrderDoesNotExistException();
		}
		orderDetailsRepo.deleteById(orderId);

	}

	public OrderDetailsVO getOrder(String orderId) throws OrderDoesNotExistException {
		Optional<OrderDetailsEntity> orderDetailsOptional = orderDetailsRepo.findById(orderId);

		if (!orderDetailsOptional.isPresent()) {
			throw new OrderDoesNotExistException();
		}
		OrderDetailsVO orderDetails = new OrderDetailsVO();
		OrderDetailsEntity orderDetailsEntity = orderDetailsOptional.get();
		orderDetails.setOrderId(orderDetailsEntity.getOrderId());
		orderDetails.setOrderName(orderDetailsEntity.getOrderName());
		orderDetails.setPrice(orderDetailsEntity.getPrice());
		return orderDetails;

	}

}
