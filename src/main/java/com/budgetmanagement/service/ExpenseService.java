package com.budgetmanagement.service;

import com.budgetmanagement.entity.Budget;
import com.budgetmanagement.entity.Category;
import com.budgetmanagement.entity.Expense;
import com.budgetmanagement.entity.User;
import com.budgetmanagement.repo.BudgetRepository;
import com.budgetmanagement.repo.CategoryRepository;
import com.budgetmanagement.repo.ExpenseRepository;
import com.budgetmanagement.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BudgetRepository budgetRepository;

	public Expense createExpense(Long userId, Long categoryId, Expense expense) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
		expense.setUser(user);
		expense.setCategory(category);
		return expenseRepository.save(expense);
	}

	public List<Expense> getExpensesByUser(Long userId) {
		return expenseRepository.findByUserId(userId);
	}

	public List<Expense> getExpensesByUserAndCategory(Long userId, Long categoryId) {
		return expenseRepository.findByUserIdAndCategoryId(userId, categoryId);
	}

	public List<Expense> getExpensesByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
		return expenseRepository.findByUserIdAndExpenseDateBetween(userId, startDate, endDate);
	}

	public Expense updateExpense(Long id, Expense updatedExpense) {
		Expense existing = expenseRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
		existing.setTitle(updatedExpense.getTitle());
		existing.setDescription(updatedExpense.getDescription());
		existing.setAmount(updatedExpense.getAmount());
		existing.setExpenseDate(updatedExpense.getExpenseDate());
		return expenseRepository.save(existing);
	}

	public void deleteExpense(Long id) {
		expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
		expenseRepository.deleteById(id);
	}

	// --- Core finance logic: budget vs actual variance + overspending alert ---
	public Map<String, Object> getBudgetVsActual(Long userId, Long categoryId, LocalDate startDate, LocalDate endDate) {
		BigDecimal totalExpense = expenseRepository.getTotalExpenseByUserAndCategoryAndDateRange(userId, categoryId,
				startDate, endDate);

		if (totalExpense == null)
			totalExpense = BigDecimal.ZERO;

		Optional<Budget> budgetOpt = budgetRepository
				.findByUserIdAndCategoryIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(userId, categoryId,
						endDate, startDate);

		Map<String, Object> result = new HashMap<>();
		result.put("totalExpense", totalExpense);

		if (budgetOpt.isPresent()) {
			BigDecimal budgetAmount = budgetOpt.get().getBudgetAmount();
			BigDecimal variance = budgetAmount.subtract(totalExpense);
			boolean isOverspent = totalExpense.compareTo(budgetAmount) > 0;

			result.put("budgetAmount", budgetAmount);
			result.put("variance", variance);
			result.put("isOverspent", isOverspent);
			result.put("alert", isOverspent ? "WARNING: You have exceeded your budget by ₹" + variance.abs()
					: "You are within budget. Remaining: ₹" + variance);
		} else {
			result.put("alert", "No budget set for this category in the given period.");
		}

		return result;
	}
}
