package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_patient;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationAlarmRepository extends JpaRepository<Tbl_medication_alarm, Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_medication_alarm> findByRegistrationCount(Integer registrationCount);

    @Transactional(readOnly = true)
    @Nonnull
    List<Tbl_medication_alarm> findAllByMedcationAlarmOnoffIsTrueAndMedicationTimeMorningOrMedicationTimeLaunchOrMedcationTimeDinner(LocalTime medicationTimeMorning, LocalTime medicationTimeLaunch, LocalTime medicationTimeDinner);

    @Transactional(readOnly = true)
    @Nonnull
    default List<Tbl_medication_alarm> findAllByEnabledIsTrueAndTime(LocalTime time) {
        return findAllByMedcationAlarmOnoffIsTrueAndMedicationTimeMorningOrMedicationTimeLaunchOrMedcationTimeDinner(time, time, time);
    }

    //Optional<Tbl_medication_alarm> findByPatientOrderByIdDesc(Tbl_patient tbl_patient);
}