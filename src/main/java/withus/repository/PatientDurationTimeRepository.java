package withus.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientDurationTimeRepository extends JpaRepository<Tbl_patient_duration_time, ProgressKey>{
    @Transactional(readOnly = true)
    Optional<Tbl_patient_duration_time> findByKey(ProgressKey progressKey);

    @Transactional(readOnly = true)
    List<Tbl_patient_duration_time> findAllByKey(ProgressKey progressKey);
}

