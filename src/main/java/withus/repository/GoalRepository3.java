package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_bottomgoals;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface GoalRepository3 extends JpaRepository<Tbl_bottomgoals, String> {
    @Transactional(readOnly = true)
    @Nonnull
    Optional<Tbl_bottomgoals> findByGoalId(String GoalId);
/*
    @Transactional(readOnly = true)
    @Nonnull
    Optional<Tbl_goal> findByGoalId(String Goal);

    @Transactional(readOnly = true)
    @Nonnull
    Optional<Tbl_goal> findByGoalId(String GoalId);

    @Transactional(readOnly = true)
    @Nonnull
    Optional<Tbl_goal> findByGoalId(String GoalId);*/

}
