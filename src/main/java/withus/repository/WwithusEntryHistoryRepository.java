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
	/**
	 * @return 지정 사용자의 {@link WwithusEntryHistory} 중,
	 * {@link withus.entity.WwithusEntry#getCode()}가 {@code codeStartsWith}로 시작하는 것들의 리스트
	 */
	@Transactional(readOnly = true)
	@NonNull
	List<WwithusEntryHistory> findAllByKey_UserAndKey_EntryCodeStartsWith(User user, String codeStartsWith);
	/**
	 * {@link WwithusEntryHistoryRepository#findAllByKey_UserAndKey_EntryCodeStartsWith(withus.entity.User, String)}를 호출하되,
	 * 주차, 일차에 해당되는 prefix를 직접 입력하지 않도록 하기 위한 default method
	 */
	@NonNull
	default List<WwithusEntryHistory> findAllByUserAndWeekDay(User user, int week, DayOfWeek dayOfWeek) {
		int day = Utility.getDayDigitForWwithus(week, dayOfWeek);
		return findAllByUserAndWeekDay(user, week, day);
	}
	/**
	 * {@link WwithusEntryHistoryRepository#findAllByKey_UserAndKey_EntryCodeStartsWith(withus.entity.User, String)}를 호출하되,
	 * 주차, 일차에 해당되는 prefix를 직접 입력하지 않도록 하기 위한 default method
	 */
	default List<WwithusEntryHistory> findAllByUserAndWeekDay(User user, int week, int day) {
		return findAllByKey_UserAndKey_EntryCodeStartsWith(user, String.format("W%dD%d", week, day));
	}
}
