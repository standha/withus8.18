package withus.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.*;
import withus.repository.GoaloptionRepository;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class GoaloptionService {

    private final GoaloptionRepository goaloptionRepository;

    public GoaloptionService(GoaloptionRepository goaloptionRepository) {
        this.goaloptionRepository = goaloptionRepository;
    }

    @Nullable
    public Tbl_goaloption getGoalId(String username) {
        return goaloptionRepository.findByGoalId(username).orElse(null);
    }

    @NonNull
    public Tbl_goaloption upsertGoal(Tbl_goaloption tbl_goaloption) {
        return goaloptionRepository.save(tbl_goaloption);
    }

    @Nullable
    public List<Tbl_goaloption> getAllGoal() {
        return goaloptionRepository.findAll();
    }

}
