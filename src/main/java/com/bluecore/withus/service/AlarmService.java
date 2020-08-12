package com.bluecore.withus.service;

import java.time.LocalDate;
import java.time.LocalTime;
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

	@NonNull
	public List<Pill> getEnabledPillsByTime(LocalTime time) {
		return pillRepository.findAllByEnabledIsTrueAndTime(time);
	}
	@Nullable
	public Pill getPill(User user) {
		return pillRepository.findTopByUserOrderByIdDesc(user).orElse(null);
	}

	/**
	 * @return 한 사용자의 전체 외래 방문 일정
	 */
	@NonNull
	public List<Appointment> getAppointments(User user) {
		return appointmentRepository.findAllByUser(user);
	}

	/**
	 * @return 한 사용자의 특정 연, 월에 해당되는 외래 방문 일정
	 * <p>
	 * 월이 바뀔 때마다 데이터를 다시 로드해야 하므로 추천하지 않음
	 */
	@NonNull
	public List<Appointment> getAppointments(User user, Year year, Month month) {
		LocalDate start = LocalDate.of(year.getValue(), month.getValue(), 1);
		LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

		return getAppointments(user, start, end);
	}
	/**
	 * @return 한 사용자의 외래 방문 일정 중 지정한 기간 내에 예정된 것들
	 */
	@NonNull
	public List<Appointment> getAppointments(User user, LocalDate startInclusive, LocalDate endInclusive) {
		return appointmentRepository.findAllByUserAndDateIsBetween(user, startInclusive, endInclusive);
	}

	@NonNull
	public List<Appointment> getEnabledAppointmentsByDate(LocalDate date) {
		return appointmentRepository.findAllByEnabledIsTrueAndDate(date);
	}

	@NonNull
	public List<Appointment> getEnabledAppointmentsByDateTime(LocalDate date, LocalTime time) {
		return appointmentRepository.findAllByEnabledIsTrueAndDateAndTime(date, time);
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
