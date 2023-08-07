package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_Exercise_record;
import withus.entity.RecordKey;
import withus.entity.Tbl_mositrue_record;
import withus.repository.ExerciseRecordRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRecordRepository exerciseRecordRepository;

    @Autowired
    public ExerciseService(ExerciseRecordRepository exerciseRecordRepository) {
        this.exerciseRecordRepository = exerciseRecordRepository;
    }


    @NonNull
    public Tbl_Exercise_record upsertExerciseRecord(Tbl_Exercise_record tbl_exercise_record) {
        RecordKey pk = tbl_exercise_record.getPk();
        Tbl_Exercise_record existingRecord = exerciseRecordRepository.findByPk(pk).orElse(null);

        // 기존 레코드가 없으면 신규로 추가
        if (existingRecord == null) {
            return exerciseRecordRepository.save(tbl_exercise_record);
        }

        // 기존 레코드가 있으면 해당 운동 종류 값 추가 또는 업데이트
        if (tbl_exercise_record.getWalking() != null) {
            existingRecord.setWalking(tbl_exercise_record.getWalking());
            existingRecord.setRecentExercise(tbl_exercise_record.getRecentExercise());

        }
        if (tbl_exercise_record.getCycling() != null) {
            existingRecord.setCycling(tbl_exercise_record.getCycling());
            existingRecord.setRecentExercise(tbl_exercise_record.getRecentExercise());

        }
        if (tbl_exercise_record.getSwimming() != null) {
            existingRecord.setSwimming(tbl_exercise_record.getSwimming());
            existingRecord.setRecentExercise(tbl_exercise_record.getRecentExercise());

        }
        if (tbl_exercise_record.getStrength() != null) {
            existingRecord.setStrength(tbl_exercise_record.getStrength());
            existingRecord.setRecentExercise(tbl_exercise_record.getRecentExercise());

        }
        updateTotalTime(existingRecord);  //총시간 업데이트

        return exerciseRecordRepository.save(existingRecord);
    }


    @Nullable
    public List<Tbl_Exercise_record> getExerciseAllRecord(String username, Integer hour, Integer minute) {
        return exerciseRecordRepository.findByPk_IdAndHourGreaterThanAndMinuteGreaterThan(username, hour, minute);
    }

    @Nullable
    public Integer getExerciseDayRecord(RecordKey pk) {
        Tbl_Exercise_record getDay = exerciseRecordRepository.findByPkAndHourIsNotNullAndMinuteIsNotNull(pk).orElse(null);
        Integer dayHour, dayMinute, dayHourToMinute;

        if (getDay == null) {
            dayHour = 0;
            dayMinute = 0;
        } else {
            dayHour = getDay.getHour();
            dayMinute = getDay.getMinute();
        }

        dayHourToMinute = (dayHour * 60) + dayMinute;

        return dayHourToMinute;
    }

    @Nullable
    public Tbl_Exercise_record getExercise(RecordKey pk) {
                return exerciseRecordRepository.findByPk(pk).orElse(null);
    }

    private void updateTotalTime(Tbl_Exercise_record record) {
        int totalMinute = 0;
        if (record.getWalking() != null) {
            totalMinute += record.getWalking();
        }
        if (record.getCycling() != null) {
            totalMinute += record.getCycling();
        }
        if (record.getSwimming() != null) {
            totalMinute += record.getSwimming();
        }
        if (record.getStrength() != null) {
            totalMinute += record.getStrength();
        }
        record.setHour(totalMinute / 60);
        record.setMinute(totalMinute % 60);
    }
}
