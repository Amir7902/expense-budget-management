package com.budgetmanagement.service;

import com.budgetmanagement.entity.Category;
import com.budgetmanagement.entity.User;
import com.budgetmanagement.repo.CategoryRepository;
import com.budgetmanagement.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	public Category createCategory(Long userId, Category category) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
		if (categoryRepository.existsByNameAndUserId(category.getName(), userId)) {
			throw new RuntimeException("Category already exists: " + category.getName());
		}
		category.setUser(user);
		return categoryRepository.save(category);
	}

	public List<Category> getCategoriesByUser(Long userId) {
		return categoryRepository.findByUserId(userId);
	}

	public Category updateCategory(Long id, Category updatedCategory) {
		Category existing = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
		existing.setName(updatedCategory.getName());
		existing.setDescription(updatedCategory.getDescription());
		return categoryRepository.save(existing);
	}

	public void deleteCategory(Long id) {
		categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
		categoryRepository.deleteById(id);
	}
}
