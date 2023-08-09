package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.RecordKey;
import withus.entity.Tbl_Exercise_record;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_mindHealth_record;

import java.util.List;
import java.util.Optional;

@Repository
public interface MindHealthRepository extends JpaRepository<Tbl_mindHealth_record, RecordKey> {

    @Transactional(readOnly = true)
    Optional<Tbl_mindHealth_record> findByPk(RecordKey pk);

    @Transactional(readOnly = true)
    List<Tbl_mindHealth_record> findByPk_Id(String id);


}

