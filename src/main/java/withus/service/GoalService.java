package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_goal;
import withus.repository.GoalRepository;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Nullable
    public Tbl_goal getGoalId(String username) {
        return goalRepository.findByGoalId(username).orElse(null);
    }

    @NonNull
    public Tbl_goal upsertGoal(Tbl_goal tbl_goal) {
        return goalRepository.save(tbl_goal);
    }

    @Nullable
    public List<Tbl_goal> getAllGoal() {
        return goalRepository.findAll();
    }
}
