package com.budgetmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.budgetmanagement.entity.Budget;
import com.budgetmanagement.service.BudgetService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

	@Autowired
	private BudgetService budgetService;

	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<Budget> createBudget(@PathVariable Long userId, @PathVariable Long categoryId,
			@Valid @RequestBody Budget budget) {
		return new ResponseEntity<>(budgetService.createBudget(userId, categoryId, budget), HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Budget>> getBudgetsByUser(@PathVariable Long userId) {
		return ResponseEntity.ok(budgetService.getBudgetsByUser(userId));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @Valid @RequestBody Budget budget) {
		return ResponseEntity.ok(budgetService.updateBudget(id, budget));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBudget(@PathVariable Long id) {
		budgetService.deleteBudget(id);
		return ResponseEntity.ok("Budget deleted successfully");
	}
}
