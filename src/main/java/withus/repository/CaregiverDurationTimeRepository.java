package withus.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaregiverDurationTimeRepository extends JpaRepository<Tbl_caregiver_duration_time, CaregiverProgressKey>{

    @Transactional(readOnly = true)
    Optional<Tbl_caregiver_duration_time> findByKey(CaregiverProgressKey caregiverProgressKey);
}

