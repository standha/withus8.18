package withus.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.*;

import java.util.Optional;

@Repository
public interface PatientDetailCountRepository extends JpaRepository<Tbl_patient_detail_button_count, ProgressKey>{
    @Transactional(readOnly = true)
    Optional<Tbl_patient_detail_button_count> findByKey(ProgressKey progressKey);
}

