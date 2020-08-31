package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_sympton_log;

import java.util.Optional;

public interface SymptonLogRepository extends JpaRepository<Tbl_sympton_log,Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_sympton_log> findByRegistrationCount (Integer registrationCount);
}
