package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.RecordKey;
import withus.entity.Tbl_caregiver_seed_day;


import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public interface CaregiverSeedDayRepository extends JpaRepository<Tbl_caregiver_seed_day, RecordKey> {

    @Nullable
    @Transactional(readOnly = true)
    Optional<Tbl_caregiver_seed_day> findByPk(RecordKey recordKey);


}