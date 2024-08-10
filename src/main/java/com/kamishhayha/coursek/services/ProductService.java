package com.kamishhayha.coursek.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kamishhayha.coursek.entities.Order;
import com.kamishhayha.coursek.entities.Product;
import com.kamishhayha.coursek.repositories.ProductRepository;
import com.kamishhayha.coursek.services.exceptions.DatabaseException;
import com.kamishhayha.coursek.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository; 
	
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		return obj.get();
	}
	
	public Product create(Product product) {
		return repository.save(product);
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

	public Product update(Long id, Product product) {
		try {
			Product entity = repository.getReferenceById(id);
			updateData(entity, product);
			return repository.save(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Product entity, Product product) {
		entity.setName(product.getName());
		entity.setDescription(product.getDescription());
		entity.setImgUrl(product.getImgUrl());
		entity.setPrice(product.getPrice());
		entity.getCategories().addAll(product.getCategories());
		
	}
	
}
