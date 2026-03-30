package com.budgetmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.budgetmanagement.entity.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<Expense> findByUserId(Long userId);

	List<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId);

	List<Expense> findByUserIdAndExpenseDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

	@Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId " + "AND e.category.id = :categoryId "
			+ "AND e.expenseDate BETWEEN :startDate AND :endDate")
	BigDecimal getTotalExpenseByUserAndCategoryAndDateRange(@Param("userId") Long userId,
			@Param("categoryId") Long categoryId, @Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);
}
