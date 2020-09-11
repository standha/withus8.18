package withus.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withus.entity.WwithusHelpRequest;

@Repository
public interface WwithusHelpRequestRepository extends JpaRepository<WwithusHelpRequest, Integer> {
	/**
	 * 향후 위더스 도우미 호출 내역 중 처리되지 않은 것을
	 * 리스팅 할 때 쓸 수 있을 것만 같은 method
	 */
	List<WwithusHelpRequest> findAllByDealtWithIsFalseOrderByDateTime();

	/**
	 * 향후 위더스 도우미 호출 내역 중 특정 기간에 발생한 것을
	 * 리스팅 할 때 쓸 수 있을 것만 같은 method
	 * @param start 검색 시작 일시
	 * @param end 검색 종료 일시
	 */
	List<WwithusHelpRequest> findAllByDateTimeIsBetween(LocalDateTime start, LocalDateTime end);
	/**
	 * 향후 위더스 도우미 호출 내역 중 특정 연도에 발생한 것을
	 * 리스팅 할 때 쓸 수 있을 것만 같은 method
	 * @param year 검색 연도
	 */
	default List<WwithusHelpRequest> findAllByYear(int year) {
		LocalDateTime start = LocalDateTime.of(LocalDate.of(year, 1, 1), LocalTime.MIN);
		LocalDateTime end = LocalDateTime.of(LocalDate.of(year, 12, 31), LocalTime.MIN.minusNanos(1));

		return findAllByDateTimeIsBetween(start, end);
	}
	/**
	 * 향후 위더스 도우미 호출 내역 중 특정 연, 월에 발생한 것을
	 * 리스팅 할 때 쓸 수 있을 것만 같은 method
	 * @param year 검색 연도
	 * @param month 검색 월 (1...12)
	 */
	default List<WwithusHelpRequest> findAllByMonth(int year, int month) {
		YearMonth yearMonth = YearMonth.of(year, month);

		LocalDateTime start = LocalDateTime.of(yearMonth.atDay(1), LocalTime.MIN);
		LocalDateTime end = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX);

		return findAllByDateTimeIsBetween(start, end);
	}
	/**
	 * 향후 위더스 도우미 호출 내역 중 특정 날짜에 발생한 것을
	 * 리스팅 할 때 쓸 수 있을 것만 같은 method
	 * @param year 검색 연도
	 * @param month 검색 월 (1...12)
	 * @param day 검색 일 (1...31)
	 */
	default List<WwithusHelpRequest> findAllByDay(int year, int month, int day) {
		LocalDate date = LocalDate.of(year, month, day);

		LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
		LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

		return findAllByDateTimeIsBetween(start, end);
	}
}
