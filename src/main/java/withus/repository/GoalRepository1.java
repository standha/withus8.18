package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_topgoals;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface GoalRepository1 extends JpaRepository<Tbl_topgoals, String> {
    @Transactional(readOnly = true)
    @Nonnull
    Optional<Tbl_topgoals> findByGoalId(String GoalId);
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
