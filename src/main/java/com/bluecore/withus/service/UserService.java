package com.bluecore.withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
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
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUserById(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Username (%s) does not exist.", username));
		}

		return user;
	}
}
