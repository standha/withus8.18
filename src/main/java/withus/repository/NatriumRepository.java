package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_natrium;

import java.util.Optional;

public interface NatriumRepository extends JpaRepository<Tbl_natrium,Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_natrium> findByRegistrationCount (Integer registrationCount);
}
