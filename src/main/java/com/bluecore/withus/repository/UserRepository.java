package com.bluecore.withus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.bluecore.withus.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	@Transactional(readOnly = true)
	Optional<User> findByIdAndPassword(String id, String password);

	Optional<User> findByContact(String contact);
}
