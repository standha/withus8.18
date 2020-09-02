package withus.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_outpatient_visit_alarm;
import withus.repository.MedicationAlarmRepository;
import withus.repository.OutPatientVisitAlarmRepository;


@Service
public class AlarmService{
    private final MedicationAlarmRepository medicationAlarmRepository;
    private final OutPatientVisitAlarmRepository outPatientVisitAlarmRepository;

    @Autowired
    public AlarmService(MedicationAlarmRepository medicationAlarmRepository, OutPatientVisitAlarmRepository outPatientVisitAlarmRepository){
        this.medicationAlarmRepository = medicationAlarmRepository;
        this.outPatientVisitAlarmRepository = outPatientVisitAlarmRepository;
    }

    @NonNull
    public Tbl_medication_alarm upsertMedication(Tbl_medication_alarm tbl_medication_alarm){
        Tbl_medication_alarm found = medicationAlarmRepository.findById(tbl_medication_alarm.getId()).orElse(null);
        found.setMedicationTimeMorning(tbl_medication_alarm.getMedicationTimeMorning());
        found.setMedicationTimeLaunch(tbl_medication_alarm.getMedicationTimeLaunch());
        found.setMedicationTimeDinner(tbl_medication_alarm.getMedicationTimeDinner());
        found.setMedicationAlarmOnoff(tbl_medication_alarm.isMedicationAlarmOnoff());
        return medicationAlarmRepository.save(found);
    }
    @NonNull
    public Tbl_outpatient_visit_alarm upsertOutPatientVisit(Tbl_outpatient_visit_alarm tbl_outpatient_visit_alarm) {
        Tbl_outpatient_visit_alarm found = outPatientVisitAlarmRepository.findById(tbl_outpatient_visit_alarm.getId()).orElse(null);
        found.setVisitAlarm(tbl_outpatient_visit_alarm.getVisitAlarm());
        found.setOutPatientVisitDate(tbl_outpatient_visit_alarm.getOutPatientVisitDate());
        found.setOutPatientVisitTime(tbl_outpatient_visit_alarm.getOutPatientVisitTime());
        return outPatientVisitAlarmRepository.save(found);
    }

}