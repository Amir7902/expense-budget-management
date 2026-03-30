package com.budgetmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.budgetmanagement.entity.Budget;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long>{

	List<Budget> findByUserId(Long userId);

	Optional<Budget> findByUserIdAndCategoryIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long userId,
			Long categoryId, LocalDate endDate, LocalDate startDate);
}
