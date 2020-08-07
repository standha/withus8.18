package com.bluecore.withus.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
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
	@NonNull
	public List<Appointment> getAppointments(User user, Year year, Month month) {
		LocalDate start = LocalDate.of(year.getValue(), month.getValue(), 1);
		LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

		return getAppointments(user, start, end);
	}
	@NonNull
	public List<Appointment> getAppointments(User user, LocalDate startInclusive, LocalDate endInclusive) {
		return appointmentRepository.findAllByUserAndDateIsBetween(user, startInclusive, endInclusive);
	}

	@Nullable
	public Appointment getAppointment(User user, LocalDate date) {
		return appointmentRepository.findTopByUserAndDateOrderByIdDesc(user, date).orElse(null);
	}

	public Pill upsertPill(Pill pill) {
		return pillRepository.save(pill);
	}

	public Appointment upsertAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}
}
