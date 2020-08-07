package com.bluecore.withus.repository.alarms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.entity.alarms.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findAllByUser(User user);
}
