package com.sahab.order.consumer.order.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahab.order.consumer.order.model.OrderUserEntity;

@Repository
public interface OrderUserRepo extends JpaRepository<OrderUserEntity, String>{
	
	List<OrderUserEntity> findAll();

}
