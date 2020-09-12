package com.sahab.order.consumer.order.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sahab.order.consumer.exceptions.UserAlreadyExistException;
import com.sahab.order.consumer.exceptions.UserDoesNotExistException;
import com.sahab.order.consumer.order.model.OrderUser;
import com.sahab.order.consumer.order.model.OrderUserEntity;
import com.sahab.order.consumer.order.repo.OrderUserRepo;

@Service
public class OrderUserService {
	@Autowired
	private OrderUserRepo userRepo;

	public List<OrderUser> getAllUsers() {
		return userRepo.findAll().stream().map(userEntity -> {
			OrderUser orderUser = new OrderUser();
			orderUser.setAddress(userEntity.getAddress());
			orderUser.setEmail(userEntity.getEmail());
			orderUser.setPhone(userEntity.getPhone());
			orderUser.setUserId(userEntity.getUserId());
			orderUser.setFirstName(userEntity.getFirstName());
			orderUser.setLastName(userEntity.getLastName());
			return orderUser;
		}).collect(Collectors.toList());
	}

	public void reisterUser(OrderUser user) throws UserAlreadyExistException {

		if (userRepo.findById(user.getUserId()).isPresent()) {
			throw new UserAlreadyExistException();
		}
		OrderUserEntity userEntity = new OrderUserEntity();
		userEntity.setAddress(user.getAddress());
		userEntity.setEmail(user.getEmail());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setPhone(user.getPhone());
		userEntity.setUserId(user.getUserId());
		userRepo.save(userEntity);

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateUser(OrderUser user) throws UserDoesNotExistException {
		Optional<OrderUserEntity> orderUserOptional = userRepo.findById(user.getUserId());

		if (!userRepo.findById(user.getUserId()).isPresent()) {
			throw new UserDoesNotExistException();
		}
		OrderUserEntity userEntity = orderUserOptional.get();
		userEntity.setAddress(user.getAddress());
		userEntity.setEmail(user.getEmail());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setPhone(user.getPhone());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteUser(String userId) throws UserDoesNotExistException {

		if (!userRepo.findById(userId).isPresent()) {
			throw new UserDoesNotExistException();
		}
		userRepo.deleteById(userId);

	}

	public OrderUser getUser(String userId) throws UserDoesNotExistException {
		Optional<OrderUserEntity> orderUserOptional = userRepo.findById(userId);

		if (!orderUserOptional.isPresent()) {
			throw new UserDoesNotExistException();
		}
		OrderUser orderUser = new OrderUser();
		OrderUserEntity userEntity = orderUserOptional.get();
		orderUser.setAddress(userEntity.getAddress());
		orderUser.setEmail(userEntity.getEmail());
		orderUser.setPhone(userEntity.getPhone());
		orderUser.setUserId(userEntity.getUserId());
		orderUser.setFirstName(userEntity.getFirstName());
		orderUser.setLastName(userEntity.getLastName());
		return orderUser;

	}

}
