package com.kamishhayha.coursek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamishhayha.coursek.entities.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
