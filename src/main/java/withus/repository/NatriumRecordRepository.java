package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_natrium_record;
import withus.entity.RecordKey;

import java.util.List;

@Repository
public interface NatriumRecordRepository extends JpaRepository<Tbl_natrium_record, RecordKey> {
    @Transactional(readOnly = true)
    List<Tbl_natrium_record>findByPk_IdAndMorningIsNotLikeAndLaunchIsNotLikeAndDinnerIsNotLike(String username, Tbl_natrium_record.Salt morning, Tbl_natrium_record.Salt launch, Tbl_natrium_record.Salt dinner);

    @Transactional(readOnly = true)
    List<Tbl_natrium_record> findByPk(RecordKey pk);
}
