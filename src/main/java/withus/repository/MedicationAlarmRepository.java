package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import withus.entity.Tbl_medication_alarm;
import withus.entity.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationAlarmRepository extends JpaRepository<Tbl_medication_alarm, String> {
    @Transactional(readOnly = true)
    @Nonnull
    Optional<Tbl_medication_alarm>findById(String id);

    @Transactional(readOnly = true)
    @Nonnull
    List<Tbl_medication_alarm>findByMedicationAlarmOnoffIsTrue();
}