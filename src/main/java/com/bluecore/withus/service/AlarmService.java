package com.bluecore.withus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.entity.alarms.Appointment;
import com.bluecore.withus.entity.alarms.Pill;
import com.bluecore.withus.repository.alarms.AppointmentRepository;
import com.bluecore.withus.repository.alarms.PillRepository;

@Service
public class AlarmService {
	private final PillRepository pillRepository;
	private final AppointmentRepository appointmentRepository;

	@Autowired
	public AlarmService(PillRepository pillRepository, AppointmentRepository appointmentRepository) {
		this.pillRepository = pillRepository;
		this.appointmentRepository = appointmentRepository;
	}

	@Nullable
	public Pill getPill(User user) {
		return pillRepository.findTopByUserOrderByIdDesc(user).orElse(null);
	}

	@NonNull
	public List<Appointment> getAppointments(User user) {
		return appointmentRepository.findAllByUser(user);
	}

	public Pill upsertPill(Pill pill) {
		return pillRepository.save(pill);
	}

	public Appointment upsertAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}
}
