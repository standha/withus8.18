package com.bluecore.withus.repository.alarms;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.entity.alarms.Pill;

public interface PillRepository extends JpaRepository<Pill, Long> {
	@NonNull
	List<Pill> findAllByEnabledIsTrueAndBreakfastOrLunchOrDinner(LocalTime breakfast, LocalTime lunch, LocalTime dinner);
	@NonNull
	default List<Pill> findAllByEnabledIsTrueAndTime(LocalTime time) {
		return findAllByEnabledIsTrueAndBreakfastOrLunchOrDinner(time, time, time);
	}

	Optional<Pill> findTopByUserOrderByIdDesc(User user);
}
