package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.security.ssl.Record;
import withus.entity.RecordKey;
import withus.entity.Tbl_Exercise_record;
import withus.entity.Tbl_weight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeightRecordRepository extends JpaRepository<Tbl_weight, RecordKey> {
    @Transactional(readOnly = true)
    List<Tbl_weight> findByPk_IdAndWeightGreaterThan(String pk_id, float weight);

    @Transactional
    Optional<Tbl_weight> findByPk(RecordKey pk);
}
