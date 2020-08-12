package com.bluecore.withus.repository.alarms;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.entity.alarms.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	@Transactional(readOnly = true)
	@NonNull
	List<Appointment> findAllByUser(User user);
	@Transactional(readOnly = true)
	@NonNull
	List<Appointment> findAllByUserAndDateIsBetween(User user, LocalDate startInclusive, LocalDate endInclusive);
	@Transactional(readOnly = true)
	@NonNull
	List<Appointment> findAllByEnabledIsTrueAndDate(LocalDate date);
	@Transactional(readOnly = true)
	@NonNull
	List<Appointment> findAllByEnabledIsTrueAndDateAndTime(LocalDate date, LocalTime time);

	@Transactional(readOnly = true)
	Optional<Appointment> findTopByUserAndDateOrderByIdDesc(User user, LocalDate date);
}
