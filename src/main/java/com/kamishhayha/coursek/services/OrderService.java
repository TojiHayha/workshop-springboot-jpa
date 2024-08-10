package com.kamishhayha.coursek.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kamishhayha.coursek.entities.Order;
import com.kamishhayha.coursek.entities.OrderItem;
import com.kamishhayha.coursek.entities.Product;
import com.kamishhayha.coursek.entities.pk.OrderItemPK;
import com.kamishhayha.coursek.repositories.OrderItemRepository;
import com.kamishhayha.coursek.repositories.OrderRepository;
import com.kamishhayha.coursek.repositories.ProductRepository;
import com.kamishhayha.coursek.services.exceptions.DatabaseException;
import com.kamishhayha.coursek.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	@Autowired
	private OrderItemRepository repositoryI;
	@Autowired
	private ProductRepository repositoryP;

	public List<Order> findAll() {
		return repository.findAll();
	}

	public Order findById(Long id) {
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
		} catch (EntityNotFoundException e) {
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

	public OrderItem addOrderItem(Long orderId, Long productId, Integer quantity) {
		Order order = repository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		Product product = repositoryP.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		OrderItem orderItem = new OrderItem(order, product, quantity, product.getPrice());

		return repositoryI.save(orderItem);
	}

	public void deleteOrderItem(Long orderId, Long productId) {
		Order order = repository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		Product product = repositoryP.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		OrderItemPK pk = new OrderItemPK();
		pk.setOrder(order);
		pk.setProduct(product);

		OrderItem orderItem = repositoryI.findById(pk).orElseThrow(() -> new ResourceNotFoundException("OrderItem not found"));

		repositoryI.delete(orderItem);
	}
}
