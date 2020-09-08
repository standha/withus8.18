package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.RecordKey;
import withus.entity.Tbl_weight;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeightRecordRepository extends JpaRepository<Tbl_weight, RecordKey> {
    @Transactional(readOnly = true)
    List<Tbl_weight>findByPk_IdAndWeightGreaterThan(String pk_id, float weight);

    @Transactional(readOnly = true)
    List<Tbl_weight>findByPkAndWeightGreaterThan(RecordKey pk, float weight);

    @Transactional(readOnly = true)
    List<Tbl_weight>findByPk_DateAndWeightGreaterThan(LocalDate pk_LocalDate, float weight);

}
