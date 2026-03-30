package com.budgetmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.budgetmanagement.entity.Expense;
import com.budgetmanagement.service.ExpenseService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<Expense> createExpense(@PathVariable Long userId, @PathVariable Long categoryId,
			@Valid @RequestBody Expense expense) {
		return new ResponseEntity<>(expenseService.createExpense(userId, categoryId, expense), HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable Long userId) {
		return ResponseEntity.ok(expenseService.getExpensesByUser(userId));
	}

	@GetMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<List<Expense>> getExpensesByUserAndCategory(@PathVariable Long userId,
			@PathVariable Long categoryId) {
		return ResponseEntity.ok(expenseService.getExpensesByUserAndCategory(userId, categoryId));
	}

	@GetMapping("/user/{userId}/daterange")
	public ResponseEntity<List<Expense>> getExpensesByDateRange(@PathVariable Long userId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return ResponseEntity.ok(expenseService.getExpensesByDateRange(userId, startDate, endDate));
	}

	@GetMapping("/user/{userId}/category/{categoryId}/variance")
	public ResponseEntity<Map<String, Object>> getBudgetVsActual(@PathVariable Long userId,
			@PathVariable Long categoryId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return ResponseEntity.ok(expenseService.getBudgetVsActual(userId, categoryId, startDate, endDate));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense expense) {
		return ResponseEntity.ok(expenseService.updateExpense(id, expense));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
		expenseService.deleteExpense(id);
		return ResponseEntity.ok("Expense deleted successfully");
	}
}
