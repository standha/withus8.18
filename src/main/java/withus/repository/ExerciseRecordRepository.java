package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.RecordKey;
import withus.entity.Tbl_Exercise_record;
import withus.entity.Tbl_mositrue_record;
import withus.entity.Tbl_natrium_record;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRecordRepository extends JpaRepository<Tbl_Exercise_record, RecordKey> {
    @Transactional(readOnly = true)
    List<Tbl_Exercise_record>findByPk_IdAndHourGreaterThanAndMinuteGreaterThan(String username, Integer Hour, Integer Minute);

    @Transactional
    Optional<Tbl_Exercise_record> findByPkAndHourIsNotNullAndMinuteIsNotNull(RecordKey pk);

    @Transactional(readOnly = true)
    Optional<Tbl_Exercise_record> findByPk(RecordKey pk);
}
