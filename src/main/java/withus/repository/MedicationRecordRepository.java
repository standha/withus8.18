package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.RecordKey;
import withus.entity.Tbl_medication_record;
import withus.entity.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRecordRepository extends JpaRepository<Tbl_medication_record, RecordKey> {
    @Transactional(readOnly = true)
    Optional<Tbl_medication_record>findByPk(RecordKey pk); //실질적으로 하나의 클래인 RecordKey를 findBy함. Getter, Setter 사용에 유의

    @Transactional(readOnly = true)
    List<Tbl_medication_record>findByPk_IdAndFinishedIsTrue(String id);

    @Transactional(readOnly = true)
    Optional<Tbl_medication_record>findByPkAndFinishedIsTrue(RecordKey pk);
}