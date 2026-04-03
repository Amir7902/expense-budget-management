package com.budgetmanagement.config;

import com.budgetmanagement.Role;
import com.budgetmanagement.entity.User;
import com.budgetmanagement.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String method = request.getMethod();
        String userId = request.getHeader("X-User-Id");

        // Skip role check for user creation endpoint
        if (request.getRequestURI().equals("/api/users")
                && method.equals("POST")) {
            return true;
        }

        // Block request if no user ID provided
        if (userId == null || userId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(
                "{\"error\": \"X-User-Id header is required\"}"
            );
            return false;
        }

        Optional<User> userOpt = userRepository
                .findById(Long.parseLong(userId));

        // Block if user not found
        if (!userOpt.isPresent()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(
                "{\"error\": \"User not found\"}"
            );
            return false;
        }

        User user = userOpt.get();

        // Block if user is inactive
        if (!user.getStatus().equals("active")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(
                "{\"error\": \"User account is inactive\"}"
            );
            return false;
        }

        Role role = user.getRole();

        // VIEWER cannot create, update or delete anything
        if (role == Role.VIEWER && (
                method.equals("POST") ||
                method.equals("PUT") ||
                method.equals("DELETE"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(
                "{\"error\": \"Access denied. VIEWER role cannot modify data\"}"
            );
            return false;
        }

        // ANALYST cannot delete anything
        if (role == Role.ANALYST && method.equals("DELETE")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(
                "{\"error\": \"Access denied. ANALYST role cannot delete records\"}"
            );
            return false;
        }

        // Attach role to request for use in controllers
        request.setAttribute("userRole", role);
        request.setAttribute("userId", Long.parseLong(userId));

        return true;
    }
}