package com.bluecore.withus.repository.alarms;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.entity.alarms.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findAllByUser(User user);
	List<Appointment> findAllByUserAndDateIsBetween(User user, LocalDate startInclusive, LocalDate endInclusive);
	Optional<Appointment> findTopByUserAndDateOrderByIdDesc(User user, LocalDate date);
}
