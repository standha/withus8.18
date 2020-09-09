package withus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import withus.entity.WwithusEntry;

public interface WwithusEntryRepository extends JpaRepository<WwithusEntry, String> {
	List<WwithusEntry> findAllByCodeStartsWithOrderByCode(String weekAndDay);
	default List<WwithusEntry> findAllByWeekAndDay(int week, int day) {
		return findAllByCodeStartsWithOrderByCode(String.format("W%dD%d", week, day));
	}
}
