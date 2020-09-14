package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.RecordKey;
import withus.entity.Tbl_goal;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Tbl_goal, RecordKey> {
    @Transactional(readOnly = true)
    Tbl_goal findByPkAndGoalGreaterThan(RecordKey pk, Integer goal);

    @Transactional(readOnly = true)
    List<Tbl_goal> findByPk_IdAndGoalGreaterThan(String pk_id, Integer goal);

    @Transactional(readOnly = true)
    Tbl_goal findByPkAndGoal(RecordKey pk, Integer goal);


}
