package withus.repository;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.User;
import withus.entity.WwithusEntryHistoryCaregiver;
import withus.util.Utility;

import java.time.DayOfWeek;
import java.util.List;

public interface WwithusEntryHistoryCaregiverRepository extends JpaRepository<WwithusEntryHistoryCaregiver, WwithusEntryHistoryCaregiver.Key> {
    /**
     * @return 지정 사용자의 {@link WwithusEntryHistoryCaregiver} 중,
     * {@link withus.entity.WwithusEntryCaregiver#getCode()}가 {@code codeStartsWith}로 시작하는 것들의 리스트
     */
    @Transactional(readOnly = true)
    @NonNull
    List<WwithusEntryHistoryCaregiver> findAllByKey_UserAndKey_EntryCodeStartsWith(User user, String codeStartsWith);

    /**
     * {@link WwithusEntryHistoryCaregiverRepository#findAllByKey_UserAndKey_EntryCodeStartsWith(User, String)}를 호출하되,
     * 주차, 일차에 해당되는 prefix를 직접 입력하지 않도록 하기 위한 default method
     */
    @NonNull
    default List<WwithusEntryHistoryCaregiver> findAllByUserAndWeekDay(User user, int week, DayOfWeek dayOfWeek) {
        int day = Utility.getDayDigitForWwithus(week, dayOfWeek);
        return findAllByUserAndWeekDay(user, week, day);
    }

    /**
     * {@link WwithusEntryHistoryCaregiverRepository#findAllByKey_UserAndKey_EntryCodeStartsWith(User, String)}를 호출하되,
     * 주차, 일차에 해당되는 prefix를 직접 입력하지 않도록 하기 위한 default method
     */
    default List<WwithusEntryHistoryCaregiver> findAllByUserAndWeekDay(User user, int week, int day) {
        return findAllByKey_UserAndKey_EntryCodeStartsWith(user, String.format("CW%dD%d", week, day));
    }


    @Transactional(readOnly = true)
    @Nullable
    @Query(value="SELECT COUNT(eh.entry_code) AS record_count " +
            "FROM wwithus_entry_history_caregiver eh " +
            "JOIN ( " +
            "    SELECT DATE(date_time) AS record_date, MAX(date_time) AS max_time " +
            "    FROM wwithus_entry_history_caregiver " +
            "    WHERE user_id = :userId " +
            "    GROUP BY DATE(date_time) " +
            ") eh_max " +
            "ON eh.date_time = eh_max.max_time AND DATE(eh.date_time) = eh_max.record_date " +
            "JOIN wwithus_entry_caregiver e ON eh.entry_code = e.code " +
            "WHERE eh.user_id = :userId " +
            "AND e.is_last = 1;", nativeQuery = true)
    Long findWwithusEntryHistoryCaregiverBy(@Param("userId") String userId);



}
