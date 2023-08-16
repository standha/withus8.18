package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.ProgressKey;
import withus.entity.Tbl_patient_sub_button_count;
import withus.entity.Tbl_seed_week;
import withus.entity.WeekUserKey;

import java.util.Optional;

@Repository
public interface SeedWeekRepository extends JpaRepository<Tbl_seed_week, WeekUserKey> {

    @Transactional(readOnly = true)
    Optional<Tbl_seed_week> findByKey(WeekUserKey key);

}
