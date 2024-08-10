package com.kamishhayha.coursek.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kamishhayha.coursek.entities.Order;
import com.kamishhayha.coursek.entities.OrderItem;
import com.kamishhayha.coursek.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	private OrderService service;
	
	@GetMapping
	public ResponseEntity<List<Order>> findAll(){
		List<Order> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id){
		Order order = service.findById(id);
		return ResponseEntity.ok().body(order);
	}
	
	@PostMapping
	public ResponseEntity<Order> create(@RequestBody Order order){
		 if (order.getPayment() != null) {
	            order.getPayment().setOrder(order);
	        }
		order = service.create(order);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();
		return ResponseEntity.created(uri).body(order);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order){
		order = service.update(id, order);
		return ResponseEntity.ok().body(order); 
	}
	
	 @PostMapping("/{orderId}/items")
	    public ResponseEntity<OrderItem> addOrderItem(@PathVariable Long orderId, 
	                                                  @RequestParam Long productId, 
	                                                  @RequestParam Integer quantity) {
	        OrderItem orderItem = service.addOrderItem(orderId, productId, quantity);
	        return ResponseEntity.ok(orderItem);
	}
	 
	 @DeleteMapping("/{orderId}/items")
	    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long orderId, 
	                                                @RequestParam Long productId) {
	        service.deleteOrderItem(orderId, productId);
	        return ResponseEntity.noContent().build();
	    }
}
