package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_Exercise_record;
import withus.entity.RecordKey;
import withus.entity.Tbl_mositrue_record;
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

    @Nullable
    public Integer getExerciseDayRecord(RecordKey pk){
        Tbl_Exercise_record getDay = exerciseRecordRepository.findByPkAndHourIsNotNullAndMinuteIsNotNull(pk).orElse(null);
        Integer dayHour;
        Integer dayMinute;
        Integer dayHourToMinute;
        if(getDay == null){ dayHour = 0; dayMinute = 0; }else{dayHour = getDay.getHour(); dayMinute = getDay.getMinute();}
        dayHourToMinute = (dayHour * 60) + dayMinute;
        return dayHourToMinute;
    }
}
