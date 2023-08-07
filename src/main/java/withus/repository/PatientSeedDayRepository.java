package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.ProgressKey;
import withus.entity.RecordKey;
import withus.entity.Tbl_patient_main_button_count;
import withus.entity.Tbl_patient_seed_day;

import javax.annotation.Nullable;

import java.util.Optional;


@Repository
public interface PatientSeedDayRepository extends JpaRepository<Tbl_patient_seed_day, RecordKey> {

    @Nullable
    @Transactional(readOnly = true)
    Optional<Tbl_patient_seed_day> findByPk(RecordKey recordKey);


}
