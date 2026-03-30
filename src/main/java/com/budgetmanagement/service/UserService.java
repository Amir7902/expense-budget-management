package com.budgetmanagement.service;

import com.budgetmanagement.entity.User;
import com.budgetmanagement.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already registered: " + user.getEmail());
		}
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public User updateUser(Long id, User updatedUser) {
		User existing = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + id));
		existing.setName(updatedUser.getName());
		existing.setEmail(updatedUser.getEmail());
		existing.setPassword(updatedUser.getPassword());
		return userRepository.save(existing);
	}

	public void deleteUser(Long id) {
		userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
		userRepository.deleteById(id);
	}
}
