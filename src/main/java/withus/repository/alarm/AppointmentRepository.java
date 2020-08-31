/*package withus.repository.alarm;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.alarm.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	@Transactional(readOnly = true)
	@NonNull
	List<Appointment> findAllByUserOrderByDateAscTimeAsc(User user);
	@Transactional(readOnly = true)
	@NonNull
	List<Appointment> findAllByUserAndDateIsBetweenOrderByDateAscTimeAsc(User user, LocalDate startInclusive, LocalDate endInclusive);
	@Transactional(readOnly = true)
	@NonNull
	List<Appointment> findAllByEnabledIsTrueAndDateOrderByTime(LocalDate date);
	@Transactional(readOnly = true)
	@NonNull
	List<Appointment> findAllByEnabledIsTrueAndDateAndTime(LocalDate date, LocalTime time);

	@Transactional(readOnly = true)
	Optional<Appointment> findTopByUserAndDateOrderByIdDesc(User user, LocalDate date);
}
*/