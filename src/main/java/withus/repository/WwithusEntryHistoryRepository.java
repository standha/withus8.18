package withus.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.User;
import withus.entity.WwithusEntryHistory;

public interface WwithusEntryHistoryRepository extends JpaRepository<WwithusEntryHistory, WwithusEntryHistory.Key> {
	@Transactional(readOnly = true)
	@NonNull
	List<WwithusEntryHistory> findAllByKey_UserOrderByDateTime(User user);

	@Transactional(readOnly = true)
	@NonNull
	List<WwithusEntryHistory> findAllByKey_UserAndDateTimeIsBetween(User user, LocalDateTime start, LocalDateTime end);
	@NonNull
	default List<WwithusEntryHistory> findAllByUserAndDate(User user, LocalDate date) {
		LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
		LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

		return findAllByKey_UserAndDateTimeIsBetween(user, start, end);
	}
}
