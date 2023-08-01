package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.RecordKey;
import withus.entity.Tbl_symptom_log;

import java.util.List;
import java.util.Optional;

@Repository
public interface SymptomRepository extends JpaRepository<Tbl_symptom_log, RecordKey> {
    @Transactional(readOnly = true)
    List<Tbl_symptom_log> findByPk_IdAndTodaysymptomGreaterThan(String pk_id, Integer todaySymptom);

    @Transactional(readOnly = true)
    Optional<Tbl_symptom_log> findByPk(RecordKey recordKey);
//    //증상기록 보기
//    @Transactional(readOnly = true)
//    List<Tbl_symptom_log> findByPk_idAndTodaySymptomAndText(String pk_id, Integer todaySymptom, String text);


}
