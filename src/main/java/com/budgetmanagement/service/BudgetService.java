package com.budgetmanagement.service;

import org.springframework.stereotype.Service;

import com.budgetmanagement.entity.Budget;
import com.budgetmanagement.entity.Category;
import com.budgetmanagement.entity.User;
import com.budgetmanagement.repo.BudgetRepository;
import com.budgetmanagement.repo.CategoryRepository;
import com.budgetmanagement.repo.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public Budget createBudget(Long userId, Long categoryId, Budget budget) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
		budget.setUser(user);
		budget.setCategory(category);
		return budgetRepository.save(budget);
	}

	public List<Budget> getBudgetsByUser(Long userId) {
		return budgetRepository.findByUserId(userId);
	}

	public Budget updateBudget(Long id, Budget updatedBudget) {
		Budget existing = budgetRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
		existing.setBudgetAmount(updatedBudget.getBudgetAmount());
		existing.setStartDate(updatedBudget.getStartDate());
		existing.setEndDate(updatedBudget.getEndDate());
		return budgetRepository.save(existing);
	}

	public void deleteBudget(Long id) {
		budgetRepository.findById(id).orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
		budgetRepository.deleteById(id);
	}
}
