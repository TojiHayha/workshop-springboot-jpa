package com.kamishhayha.coursek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamishhayha.coursek.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}
