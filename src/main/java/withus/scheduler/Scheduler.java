package withus.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import withus.entity.alarm.Appointment;
import withus.entity.alarm.Pill;
import withus.service.AlarmService;

@Component
@EnableAsync
public class Scheduler {
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

	private final AlarmService alarmService;

	@Autowired
	public Scheduler(AlarmService alarmService) {
		this.alarmService = alarmService;
	}

	@Scheduled(cron = "0 * * * * *")
	public void runEveryMinute() {
		LocalDateTime now = LocalDateTime.now();
		LocalDate date = now.toLocalDate();
		LocalTime time = now.toLocalTime();

		List<Pill> pills = alarmService.getEnabledPillsByTime(time);
		for (Pill pill : pills) {
			// TODO: 복약 push 발송 api 호출
			logger.info("TODO: push 발송 api 호출 for ({})", pill);
		}

		List<Appointment> appointments = alarmService.getEnabledAppointmentsByDateTime(date, time.plusHours(2));
		for (Appointment appointment : appointments) {
			// TODO: 2시간 후 외래 방문 일정 push 발송 api 호출
			logger.info("TODO: 2시간 후 외래 방문 일정 push 발송 api 호출 for ({})", appointment);
		}
	}

	@Scheduled(cron = "0 0 18 * * *")
	public void runAt18() {
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		List<Appointment> appointments = alarmService.getEnabledAppointmentsByDate(tomorrow);
		for (Appointment appointment : appointments) {
			// TODO: 내일 외래 방문 일정 push 발송 api 호출
			logger.info("TODO: 내일 외래 방문 일정 push 발송 api 호출 for ({})", appointment);
		}
	}
}
