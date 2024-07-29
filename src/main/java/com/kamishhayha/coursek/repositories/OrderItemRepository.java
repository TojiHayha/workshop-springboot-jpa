package com.kamishhayha.coursek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamishhayha.coursek.entities.OrderItem;
import com.kamishhayha.coursek.entities.pk.OrderItemPK;


public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
