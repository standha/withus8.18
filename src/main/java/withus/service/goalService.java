package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_goal;
import withus.repository.GoalRepository;
import javax.annotation.Nullable;

@Service
public class goalService {

    private final GoalRepository goalRepository;

    @Autowired
    public goalService(GoalRepository goalRepository){
        this.goalRepository = goalRepository;
    }

    @Nullable
    public Tbl_goal getGoalId(String username){
        return goalRepository.findByGoalId(username).orElse(null);
    }
}
