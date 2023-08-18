package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_goaloption;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface GoaloptionRepository extends JpaRepository<Tbl_goaloption, String> {
    @Transactional(readOnly = true)
    @Nonnull
    Optional<Tbl_goaloption> findByGoalId(String Goaloption);

}