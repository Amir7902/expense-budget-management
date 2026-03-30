package com.budgetmanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budgets")
public class Budget {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotNull(message = "Budget amount is required")
	    @Positive(message = "Budget amount must be greater than zero")
	    @Column(name = "budget_amount", nullable = false, precision = 10, scale = 2)
	    private BigDecimal budgetAmount;

	    @NotNull(message = "Start date is required")
	    @Column(name = "start_date", nullable = false)
	    private LocalDate startDate;

	    @NotNull(message = "End date is required")
	    @Column(name = "end_date", nullable = false)
	    private LocalDate endDate;

	    @Column(name = "created_at", updatable = false)
	    private LocalDateTime createdAt;

	    @PrePersist
	    protected void onCreate() {
	        this.createdAt = LocalDateTime.now();
	    }

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "category_id", nullable = false)
	    private Category category;
}
