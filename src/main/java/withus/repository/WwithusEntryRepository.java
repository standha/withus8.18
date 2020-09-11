package withus.repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.WwithusEntry;
import withus.util.Utility;

public interface WwithusEntryRepository extends JpaRepository<WwithusEntry, String> {
	/**
	 * @return {@link withus.entity.WwithusEntry} 중,
	 * {@link withus.entity.WwithusEntry#isAnswer()}가 true이며,
	 * {@link withus.entity.WwithusEntry#getCode()}가
	 * {@code not}이 아니며 {@code startsWith}로 시작하는 것들의 리스트
	 */
	@Transactional(readOnly = true)
	List<WwithusEntry> findAllByAnswerIsTrueAndCodeIsNotAndCodeStartsWithOrderByCode(String not, String startsWith);
	/**
	 * {@link withus.repository.WwithusEntryRepository#findAllByAnswerIsTrueAndCodeIsNotAndCodeStartsWithOrderByCode(String, String)}를 호출하되,
	 * {@code not}과 {@code startsWith}를 하나의 동일한 값({@code startsWith})으로 전달
	 * <p>
	 * 예를 들어 "W1D1_2"에 대한 답변인 "W1D1_2_A1", "W1D1_2_A2"를 select 해 올 때 유용하다.
	 */
	default List<WwithusEntry> findAllAnswersByCodeStartsWithButNotExactlyOrderByCode(String startsWith) {
		return findAllByAnswerIsTrueAndCodeIsNotAndCodeStartsWithOrderByCode(startsWith, startsWith);
	}

	/**
	 * @return {@link withus.entity.WwithusEntry} 중,
	 * {@link withus.entity.WwithusEntry#isFirst()}가 true이며,
	 * {@link withus.entity.WwithusEntry#getCode()}가 {@code code}로 시작하는 것
	 */
	@Transactional(readOnly = true)
	Optional<WwithusEntry> findTopByCodeStartsWithAndFirstIsTrueOrderByCode(String code);
	/**
	 * {@link withus.repository.WwithusEntryRepository#findTopByCodeStartsWithAndFirstIsTrueOrderByCode(String)}를 호출하되,
	 * 주차, 일차에 해당되는 prefix를 직접 입력하지 않도록 하기 위한 default method
	 */
	default Optional<WwithusEntry> findFirstByWeekAndDay(int week, DayOfWeek dayOfWeek) {
		int day = Utility.getDayDigitForWwithus(week, dayOfWeek);
		return findFirstByWeekAndDay(week, day);
	}
	/**
	 * {@link withus.repository.WwithusEntryRepository#findTopByCodeStartsWithAndFirstIsTrueOrderByCode(String)}를 호출하되,
	 * 주차, 일차에 해당되는 prefix를 직접 입력하지 않도록 하기 위한 default method
	 */
	default Optional<WwithusEntry> findFirstByWeekAndDay(int week, int day) {
		return findTopByCodeStartsWithAndFirstIsTrueOrderByCode(String.format("W%dD%d", week, day));
	}
}
