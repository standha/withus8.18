package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_bottomgoals;
import withus.entity.Tbl_goal;
import withus.entity.Tbl_middlegoals;
import withus.entity.Tbl_topgoals;
import withus.repository.GoalRepository;
import withus.repository.GoalRepository1;
import withus.repository.GoalRepository2;
import withus.repository.GoalRepository3;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalRepository1 goalRepository1;
    private final GoalRepository2 goalRepository2;
    private final GoalRepository3 goalRepository3;

    @Autowired
    public GoalService(GoalRepository goalRepository, GoalRepository1 goalRepository1, GoalRepository2 goalRepository2, GoalRepository3 goalRepository3) {
        this.goalRepository = goalRepository;
        this.goalRepository1 = goalRepository1;
        this.goalRepository2 = goalRepository2;
        this.goalRepository3 = goalRepository3;
    }

    @Nullable
    public Tbl_goal getGoalId(String username) {
        return goalRepository.findByGoalId(username).orElse(null);
    }
    @Nullable
    public Tbl_topgoals getGoalId1(String username) {
        return goalRepository1.findByGoalId(username).orElse(null);
    }
    @Nullable
    public Tbl_middlegoals getGoalId2(String username) {
        return goalRepository2.findByGoalId(username).orElse(null);
    }
    @Nullable
    public Tbl_bottomgoals getGoalId3(String username) {
        return goalRepository3.findByGoalId(username).orElse(null);
    }

    @NonNull
    public Tbl_goal upsertGoal(Tbl_goal tbl_goal) {
        return goalRepository.save(tbl_goal);
    }
    @NonNull
    public Tbl_topgoals upsert1Goal(Tbl_topgoals tbl_topgoals) { return goalRepository1.save(tbl_topgoals);
    }
    @NonNull
    public Tbl_middlegoals upsert2Goal(Tbl_middlegoals tbl_middlegoals) { return goalRepository2.save(tbl_middlegoals);
    }
    @NonNull
    public Tbl_bottomgoals upsert3Goal(Tbl_bottomgoals tbl_bottomgoals) { return goalRepository3.save(tbl_bottomgoals);
    }

    @Nullable
    public List<Tbl_goal> getAllGoal() {
        return goalRepository.findAll();
    }
}
