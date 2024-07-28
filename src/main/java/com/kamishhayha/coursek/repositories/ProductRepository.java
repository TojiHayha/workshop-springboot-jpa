package com.kamishhayha.coursek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamishhayha.coursek.entities.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
