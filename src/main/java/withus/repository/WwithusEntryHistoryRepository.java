package withus.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.User;
import withus.entity.WwithusEntryHistory;
import withus.util.Utility;

public interface WwithusEntryHistoryRepository extends JpaRepository<WwithusEntryHistory, WwithusEntryHistory.Key> {
	@Transactional(readOnly = true)
	@NonNull
	List<WwithusEntryHistory> findAllByKey_UserAndKey_EntryCodeStartsWith(User user, String codeStartsWith);
	@NonNull
	default List<WwithusEntryHistory> findAllByUserAndWeekDay(User user, int week, DayOfWeek dayOfWeek) {
		int day = Utility.getDayDigitForWwithus(week, dayOfWeek);
		return findAllByUserAndWeekDay(user, week, day);
	}
	default List<WwithusEntryHistory> findAllByUserAndWeekDay(User user, int week, int day) {
		return findAllByKey_UserAndKey_EntryCodeStartsWith(user, String.format("W%dD%d", week, day));
	}
}
