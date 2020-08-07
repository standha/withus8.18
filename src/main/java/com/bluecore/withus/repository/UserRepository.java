package com.bluecore.withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecore.withus.entity.User;

public interface UserRepository extends JpaRepository<User, String> { }
