package com.bluecore.withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Nullable
	public User getUserById(String id) {
		return userRepository.findById(id).orElse(null);
	}
	@Nullable
	public User getUserByIdPassword(String id, String password) {
		return userRepository.findByIdAndPassword(id, password).orElse(null);
	}

	@Nullable
	public User saveUser(User user) {
		return userRepository.save(user);
	}
}
