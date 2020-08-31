package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_button_count;

import java.util.Optional;

public interface ButtonCountRepository extends JpaRepository<Tbl_button_count,Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_button_count> findByRegistrationCount (Integer registrationCount);
}
