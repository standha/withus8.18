package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_Exercise_record;
import withus.entity.RecordKey;
import withus.repository.ExerciseRecordRepository;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRecordRepository exerciseRecordRepository;

    @Autowired
    public ExerciseService(ExerciseRecordRepository exerciseRecordRepository){
        this.exerciseRecordRepository = exerciseRecordRepository;
    }

    @NonNull
    public Tbl_Exercise_record upsertExerciseRecord(Tbl_Exercise_record tbl_exercise_record){
        Tbl_Exercise_record saved = exerciseRecordRepository.save(tbl_exercise_record);
        return saved;
    }

    @Nullable
    public List<Tbl_Exercise_record> getExerciseAllRecord(String username, Integer hour, Integer minute){
        return exerciseRecordRepository.findByPk_IdAndHourGreaterThanAndMinuteGreaterThan(username, hour, minute);
    }
}
