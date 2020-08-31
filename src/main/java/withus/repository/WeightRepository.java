package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_weight;

import java.util.Optional;

public interface WeightRepository extends JpaRepository<Tbl_weight,Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_weight> findByRegistrationCount (Integer registrationCount);
}
