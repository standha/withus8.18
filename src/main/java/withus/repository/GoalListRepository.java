package withus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_goal_list;

import java.util.Optional;

@Repository
public interface GoalListRepository extends JpaRepository<Tbl_goal_list, Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_goal_list> findByRegistrationCount (Integer registrationCount);

}
