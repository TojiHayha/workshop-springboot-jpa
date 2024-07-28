package com.kamishhayha.coursek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamishhayha.coursek.entities.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
