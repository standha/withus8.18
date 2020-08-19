package withus.repository.alarm;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.User;
import withus.entity.alarm.Pill;

@Repository
public interface PillRepository extends JpaRepository<Pill, Long> {
	@Transactional(readOnly = true)
	@NonNull
	List<Pill> findAllByEnabledIsTrueAndBreakfastOrLunchOrDinner(LocalTime breakfast, LocalTime lunch, LocalTime dinner);
	@Transactional(readOnly = true)
	@NonNull
	default List<Pill> findAllByEnabledIsTrueAndTime(LocalTime time) {
		return findAllByEnabledIsTrueAndBreakfastOrLunchOrDinner(time, time, time);
	}

	@Transactional(readOnly = true)
	Optional<Pill> findTopByUserOrderByIdDesc(User user);
}
