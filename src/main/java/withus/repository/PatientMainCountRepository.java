package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.ProgressKey;
import withus.entity.Tbl_patient_main_button_count;

import java.util.Optional;

@Repository
public interface PatientMainCountRepository extends JpaRepository<Tbl_patient_main_button_count, ProgressKey> {

    @Transactional(readOnly = true)
    Optional<Tbl_patient_main_button_count> findByKey(ProgressKey progressKey);

}
