package com.bluecore.withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bluecore.withus.auth.NoOpPasswordEncoder;
import com.bluecore.withus.entity.User;
import com.bluecore.withus.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@NonNull
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findById(username).orElseThrow(() -> {
				String message = String.format("Username \"%s\" does not exist!", username);
				return new UsernameNotFoundException(message);
			}
		);
	}

	@Nullable
	public User getUserById(String id) {
		return userRepository.findById(id).orElse(null);
	}

	@Nullable
	public User getUserByContact(String contact) {
		return userRepository.findByContact(contact).orElse(null);
	}

	@NonNull
	public User upsertUser(User user) {
		return userRepository.save(user);
	}
	@NonNull
	public User upsertUserEncodingPassword(User user) {
		String plaintextPassword = user.getPassword();

		NoOpPasswordEncoder noOpPasswordEncoder = NoOpPasswordEncoder.getInstance();
		String encodedPassword = noOpPasswordEncoder.encode(plaintextPassword);
		user.setPassword(encodedPassword);

		return userRepository.save(user);
	}
}
