package com.bluecore.withus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecore.withus.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByIdAndPassword(String id, String password);

	Optional<User> findByContact(String contact);
}
