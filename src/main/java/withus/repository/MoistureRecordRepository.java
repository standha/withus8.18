package withus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.RecordKey;
import withus.entity.Tbl_mositrue_record;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface MoistureRecordRepository extends JpaRepository<Tbl_mositrue_record, RecordKey> {
    @Transactional(readOnly = true)
    List<Tbl_mositrue_record>findByPk_IdAndIntakeGreaterThan(String pk_id, Integer intake);

    @Transactional(readOnly = true)
    List<Tbl_mositrue_record>findByPkAndIntakeGreaterThan(RecordKey pk, Integer intake);

    @Transactional
    Optional<Tbl_mositrue_record>findByPkAndIntakeIsNotNull(RecordKey pk);

}

