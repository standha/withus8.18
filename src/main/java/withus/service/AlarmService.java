package withus.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_patient;
import withus.repository.MedicationAlarmRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AlarmService{
    private final MedicationAlarmRepository medicationAlarmRepository;

    @Autowired
    public AlarmService(MedicationAlarmRepository medicationAlarmRepository){
        this.medicationAlarmRepository = medicationAlarmRepository;
    }

   // @NonNull
 //   public Tbl_medication_alarm getMedicationAlarmByPatient(Tbl_patient tbl_patient){return medicationAlarmRepository.findByPatientOrderByIdDesc(tbl_patient).orElse(null);}

    @Nullable
    public List<Tbl_medication_alarm> getOnOffMedicationByTime(LocalTime time) {return medicationAlarmRepository.findAllByEnabledIsTrueAndTime(time);}
/*
    @Nullable
    public Tbl_medication_alarm getMedicationByPatient(Tbl_patient tbl_patient){
        return medicationAlarmRepository.findByPatientOrderByIdDesc(tbl_patient).orElse(null);
    }
*/
    @NonNull Tbl_medication_alarm upsertMedcationAlarm(Tbl_medication_alarm tbl_medication_alarm){return medicationAlarmRepository.save(tbl_medication_alarm);}

}