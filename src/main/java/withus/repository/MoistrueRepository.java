package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_moisture;

import java.util.Optional;

public interface MoistrueRepository extends JpaRepository<Tbl_moisture,Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_moisture> findByRegistrationCount (Integer registrationCount);
}
