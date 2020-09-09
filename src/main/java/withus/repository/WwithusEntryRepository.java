package withus.repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.WwithusEntry;
import withus.util.Utility;

public interface WwithusEntryRepository extends JpaRepository<WwithusEntry, String> {
	@Transactional(readOnly = true)
	List<WwithusEntry> findAllByAnswerIsTrueAndCodeIsNotAndCodeStartsWithOrderByCode(String not, String startsWith);
	default List<WwithusEntry> findAllAnswersByCodeStartsWithButNotExactlyOrderByCode(String startsWith) {
		return findAllByAnswerIsTrueAndCodeIsNotAndCodeStartsWithOrderByCode(startsWith, startsWith);
	}

	@Transactional(readOnly = true)
	Optional<WwithusEntry> findTopByCodeStartsWithAndFirstIsTrueOrderByCode(String code);
	default Optional<WwithusEntry> findFirstByWeekAndDay(int week, DayOfWeek dayOfWeek) {
		int day = Utility.getDayDigitForWwithus(week, dayOfWeek);
		return findTopByCodeStartsWithAndFirstIsTrueOrderByCode(String.format("W%dD%d", week, day));
	}
}
