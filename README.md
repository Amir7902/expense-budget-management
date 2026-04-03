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

# Role-Based Access Control

The system supports three user roles:

| Role    | Create | Read | Update | Delete |
|---------|--------|------|--------|--------|
| ADMIN   | ✅     | ✅   | ✅     | ✅     |
| ANALYST | ✅     | ✅   | ✅     | ❌     |
| VIEWER  | ❌     | ✅   | ❌     | ❌     |

Every API request (except user creation) requires an X-User-Id header.
The system checks the user's role and blocks unauthorized actions automatically.

## Dashboard Summary API

GET /api/expenses/user/{userId}/dashboard

Returns:
- Total income (sum of all budgets)
- Total expenses
- Net balance
- Category-wise expense totals
- Total number of records
