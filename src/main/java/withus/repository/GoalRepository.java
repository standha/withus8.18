package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_goal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Tbl_goal, String> {
    @Transactional(readOnly = true)
    @Nonnull
    Optional<Tbl_goal> findByGoalId(String GoalId);

}
