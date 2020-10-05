package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_natrium_record;
import withus.entity.RecordKey;

import java.util.List;
import java.util.Optional;

@Repository
public interface NatriumRecordRepository extends JpaRepository<Tbl_natrium_record, RecordKey> {
    @Transactional(readOnly = true)
    List<Tbl_natrium_record>findByPkIsNotNull();

    @Transactional(readOnly = true)
    Optional<Tbl_natrium_record> findByPk(RecordKey pk);

    @Transactional(readOnly = true)
    List<Tbl_natrium_record>findByPk_Id(String id);

}
