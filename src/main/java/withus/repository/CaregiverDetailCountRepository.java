package withus.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.CaregiverProgressKey;
import withus.entity.Tbl_caregiver_detail_button_count;


import java.util.Optional;

@Repository
public interface CaregiverDetailCountRepository extends JpaRepository<Tbl_caregiver_detail_button_count, CaregiverProgressKey>{
    @Transactional(readOnly = true)
    Optional<Tbl_caregiver_detail_button_count> findByKey(CaregiverProgressKey caregiverProgressKey);
}
