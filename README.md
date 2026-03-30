# Expense & Budget Management System

A Spring Boot REST API for managing personal expenses and monthly budgets with automated overspending alerts.

## Tech Stack
Java • Spring Boot • Hibernate/JPA • MySQL • REST APIs • Postman • Swagger

## Features
- User and category management
- Expense tracking with date range filtering
- Monthly budget setting per category
- Budget vs actual variance calculation
- Automated overspending alerts with remaining balance

## API Endpoints
- POST /api/users — Create user
- POST /api/categories/user/{userId} — Create category
- POST /api/expenses/user/{userId}/category/{categoryId} — Add expense
- POST /api/budgets/user/{userId}/category/{categoryId} — Set budget
- GET /api/expenses/user/{userId}/category/{categoryId}/variance — Budget vs actual report

## How to Run
1. Clone the repository
2. Create MySQL database: CREATE DATABASE expense_budget_db;
3. Update application.properties with your MySQL credentials
4. Run ExpenceAndBudgetManagementApplication.java
5. Test APIs on http://localhost:8080
