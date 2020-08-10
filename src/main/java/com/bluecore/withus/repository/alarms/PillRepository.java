package com.bluecore.withus.repository.alarms;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.entity.alarms.Pill;

public interface PillRepository extends JpaRepository<Pill, Long> {
	Optional<Pill> findTopByUserOrderByIdDesc(User user);
}
