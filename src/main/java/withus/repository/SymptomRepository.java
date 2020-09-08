package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.RecordKey;
import withus.entity.Tbl_mositrue_record;
import withus.entity.Tbl_symptom_log;

import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository <Tbl_symptom_log, RecordKey> {

    @Transactional(readOnly = true)
    List<Tbl_symptom_log> findByPkAndAndTodaySymptomGreaterThan(RecordKey pk, Integer sum);
}
