package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_medication_alarm;
import withus.repository.MedicationAlarmRepository;
import withus.repository.MedicationRecordRepository;

@Service
public class MedicationService {
    private final MedicationAlarmRepository medicationAlarmRepository;
    @Autowired
    public MedicationService(MedicationAlarmRepository medicationAlarmRepository){
        this.medicationAlarmRepository = medicationAlarmRepository;
    }
    @NonNull
    public Tbl_medication_alarm upsertAlarm(Tbl_medication_alarm alarmOnoffMorning) {
        return medicationAlarmRepository.save(alarmOnoffMorning);
    }


}
