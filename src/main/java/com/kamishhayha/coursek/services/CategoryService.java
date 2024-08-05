package com.kamishhayha.coursek.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kamishhayha.coursek.entities.Category;
import com.kamishhayha.coursek.repositories.CategoryRepository;
import com.kamishhayha.coursek.services.exceptions.DatabaseException;
import com.kamishhayha.coursek.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

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

	public Category create(Category category) {
		return repository.save(category);
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
	
	public Category update(Long id, Category category) {
		try{
			Category entity = repository.getReferenceById(id);
			updateData(entity, category);
			return repository.save(entity);
			
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateData(Category entity, Category category) {
		entity.setName(category.getName());
	}

}
