package com.kamishhayha.coursek.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kamishhayha.coursek.entities.Order;
import com.kamishhayha.coursek.entities.User;
import com.kamishhayha.coursek.repositories.OrderRepository;
import com.kamishhayha.coursek.services.exceptions.DatabaseException;
import com.kamishhayha.coursek.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository; 
	
	public List<Order> findAll() {
		return repository.findAll();
	}
	
	public Order findByid(Long id) {
		Optional<Order> obj = repository.findById(id);
		return obj.get();
	}
	
	public Order create(Order order) {
		return repository.save(order);
	}

	public void delete(Long id) {
		try {
			if (!repository.existsById(id)) {
				throw new ResourceNotFoundException(id);
			}
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Order update(Long id, Order order) {
		try {
			Order entity = repository.getReferenceById(id);
			updateData(entity, order);
			return repository.save(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Order entity, Order order) {
		entity.setMoment(order.getMoment());
		entity.setOrderStatus(order.getOrderStatus());
		entity.setClient(order.getClient());
		entity.setPayment(order.getPayment());
		entity.getItems().addAll(order.getItems());

	}
	
}
