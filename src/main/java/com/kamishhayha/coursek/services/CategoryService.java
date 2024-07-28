package com.kamishhayha.coursek.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kamishhayha.coursek.entities.Category;
import com.kamishhayha.coursek.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository; 
	
	public List<Category> findAll() {
		return repository.findAll();
	}
	
	public Category findByid(Long id) {
		Optional<Category> obj = repository.findById(id);
		return obj.get();
	}
	
}
