package com.bluecore.withus.repository.alarm;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.entity.alarm.Pill;
import com.bluecore.withus.entity.alarm.PillHistory;

@Repository
public interface PillHistoryRepository extends JpaRepository<PillHistory, Long> {
	@Transactional(readOnly = true)
	@NonNull
	List<PillHistory> findAllByFinishedIsTrueAndPill(Pill pill);
	@Transactional(readOnly = true)
	@NonNull
	List<PillHistory> findAllByFinishedIsTrueAndPillUserOrderByDate(User user);

	@Transactional(readOnly = true)
	@NonNull
	List<PillHistory> findAllByFinishedIsTrueAndPillUserAndDateBetweenOrderByDate(User user, LocalDate startInclusive, LocalDate endInclusive);

	@Transactional(readOnly = true)
	Optional<PillHistory> findTopByPillUserAndDateOrderByIdDesc(User user, LocalDate date);
}
