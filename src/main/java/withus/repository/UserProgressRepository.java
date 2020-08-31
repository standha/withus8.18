package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_user_progress;

import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository <Tbl_user_progress, Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_user_progress> findByRegistrationCount (Integer registrationCount);
}
