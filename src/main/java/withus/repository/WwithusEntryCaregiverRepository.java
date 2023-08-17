package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.WwithusEntryCaregiver;
import withus.util.Utility;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface WwithusEntryCaregiverRepository extends JpaRepository<WwithusEntryCaregiver, String> {
    /**
     * @return {@link WwithusEntryCaregiver} 중,
     * {@link WwithusEntryCaregiver#isAnswer()}가 true이며,
     * {@link WwithusEntryCaregiver#getCode()}가 {@code not}이 아니며
     * {@code startsWith}로 시작하는 것들의 리스트
     */
    @Transactional(readOnly = true)
    List<WwithusEntryCaregiver> findAllByAnswerIsTrueAndCodeIsNotAndCodeStartsWithOrderByCode(String not, String startsWith);

    /**
     * {@link WwithusEntryCaregiverRepository#findAllByAnswerIsTrueAndCodeIsNotAndCodeStartsWithOrderByCode(String, String)}를
     * 호출하되, {@code not}과 {@code startsWith}를 하나의 동일한 값({@code startsWith})으로 전달
     * <p>
     * 예를 들어 "W1D1_2"에 대한 답변인 "W1D1_2_A1", "W1D1_2_A2"를 select 해 올 때 유용하다.
     */
    default List<WwithusEntryCaregiver> findAllAnswersByCodeStartsWithButNotExactlyOrderByCode(String startsWith) {
        return findAllByAnswerIsTrueAndCodeIsNotAndCodeStartsWithOrderByCode(startsWith, startsWith);
    }

    /**
     * @return {@link WwithusEntryCaregiver} 중,
     * {@link WwithusEntryCaregiver#isFirst()}가 true이며,
     * {@link WwithusEntryCaregiver#getCode()}가 {@code code}로 시작하는 것
     */
    @Transactional(readOnly = true)
    Optional<WwithusEntryCaregiver> findTopByCodeStartsWithAndFirstIsTrueOrderByCode(String code);

    /**
     * {@link WwithusEntryCaregiverRepository#findTopByCodeStartsWithAndFirstIsTrueOrderByCode(String)}를
     * 호출하되, 주차, 일차에 해당되는 prefix를 직접 입력하지 않도록 하기 위한 default method
     */
    default Optional<WwithusEntryCaregiver> findFirstByWeekAndDay(int week, DayOfWeek dayOfWeek) {
        int day = Utility.getDayDigitForWwithus(week, dayOfWeek);
        return findFirstByWeekAndDay(week, day);
    }

    /**
     * {@link WwithusEntryCaregiverRepository#findTopByCodeStartsWithAndFirstIsTrueOrderByCode(String)}를
     * 호출하되, 주차, 일차에 해당되는 prefix를 직접 입력하지 않도록 하기 위한 default method
     */
    default Optional<WwithusEntryCaregiver> findFirstByWeekAndDay(int week, int day) {
        System.out.println("week = " + week);
        System.out.println("day = " + day);
        return findTopByCodeStartsWithAndFirstIsTrueOrderByCode(String.format("CW%dD%d", week, day));
    }
}
