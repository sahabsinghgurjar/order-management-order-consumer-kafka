package com.sahab.order.consumer.order.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahab.order.consumer.order.model.OrderDetailsEntity;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetailsEntity, String>{
	
	List<OrderDetailsEntity> findAll();

}
