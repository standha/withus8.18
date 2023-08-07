package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import withus.entity.RecordKey;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_natrium_record;
import withus.entity.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationAlarmRepository extends JpaRepository<Tbl_medication_alarm, RecordKey> {

    @Transactional(readOnly = true)
    Optional<Tbl_medication_alarm> findByPk(RecordKey pk);

    @Transactional(readOnly = true)
    List<Tbl_medication_alarm> findByPk_Id(String id);


}