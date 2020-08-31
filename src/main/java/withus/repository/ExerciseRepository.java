package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_exercise;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Tbl_exercise,Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_exercise> findByRegistrationCount (Integer registrationCount);
}
