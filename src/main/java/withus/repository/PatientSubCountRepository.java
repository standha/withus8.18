package withus.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.CaregiverProgressKey;
import withus.entity.ProgressKey;
import withus.entity.Tbl_caregiver_sub_button_count;
import withus.entity.Tbl_patient_sub_button_count;

import java.util.Optional;

@Repository
public interface PatientSubCountRepository extends JpaRepository<Tbl_patient_sub_button_count, ProgressKey>{
    @Transactional(readOnly = true)
    Optional<Tbl_patient_sub_button_count> findByKey(ProgressKey progressKey);
}
