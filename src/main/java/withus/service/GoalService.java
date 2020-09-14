package withus.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_goal;
import withus.repository.GoalRepository;

import java.util.List;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository){
        this.goalRepository = goalRepository;
    }

    @NonNull
    public Tbl_goal getGoalDateRecord(RecordKey pk, Integer goal){
        return goalRepository.findByPkAndGoalGreaterThan(pk, goal);
    }

    @NonNull
    public List<Tbl_goal> getGoalRecord(String id, Integer goal){
        return goalRepository.findByPk_IdAndGoalGreaterThan(id, goal);
    }

    @NonNull
    public Tbl_goal getThisWeekRecord(RecordKey pk, Integer goal){
        return goalRepository.findByPkAndGoal(pk, goal);
    }

    @NonNull
    public Tbl_goal upsertGoal(Tbl_goal tbl_goal){
        Tbl_goal saved = goalRepository.save(tbl_goal);
        return saved;
    }
}
