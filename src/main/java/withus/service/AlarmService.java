package withus.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.*;
import withus.repository.MedicationAlarmRepository;
import withus.repository.MedicationRecordRepository;
import withus.repository.OutPatientVisitAlarmRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;


@Service
public class AlarmService{
    private final MedicationAlarmRepository medicationAlarmRepository;
    private final OutPatientVisitAlarmRepository outPatientVisitAlarmRepository;
    private final MedicationRecordRepository medicationRecordRepository;


    @Autowired
    public AlarmService(MedicationRecordRepository medicationRecordRepository, MedicationAlarmRepository medicationAlarmRepository, OutPatientVisitAlarmRepository outPatientVisitAlarmRepository){
        this.medicationAlarmRepository = medicationAlarmRepository;
        this.medicationRecordRepository = medicationRecordRepository;
        this.outPatientVisitAlarmRepository = outPatientVisitAlarmRepository;
    }
    @Nonnull
    public Tbl_medication_record upsertTrueRecord(Tbl_medication_record tbl_medication_record){
        Tbl_medication_record saved = medicationRecordRepository.save(tbl_medication_record);
        return saved;
    }
    @NonNull
    public Tbl_medication_alarm upsertMedication(Tbl_medication_alarm tbl_medication_alarm){
            Tbl_medication_alarm found = medicationAlarmRepository.findById(tbl_medication_alarm.getId()).orElse(null);
            found.setMedicationTimeMorning(tbl_medication_alarm.getMedicationTimeMorning());
            found.setMedicationTimeLunch(tbl_medication_alarm.getMedicationTimeLunch());
            found.setMedicationTimeDinner(tbl_medication_alarm.getMedicationTimeDinner());
            found.setMedicationAlarmOnoff(tbl_medication_alarm.isMedicationAlarmOnoff());

        Tbl_medication_alarm saved = medicationAlarmRepository.save(tbl_medication_alarm);
        return saved;
    }
    @NonNull
    public Tbl_outpatient_visit_alarm upsertOutPatientVisit(Tbl_outpatient_visit_alarm tbl_outpatient_visit_alarm) {
        Tbl_outpatient_visit_alarm found = outPatientVisitAlarmRepository.findById(tbl_outpatient_visit_alarm.getId()).orElse(null);
        found.setVisitAlarm(tbl_outpatient_visit_alarm.getVisitAlarm());
        found.setOutPatientVisitDate(tbl_outpatient_visit_alarm.getOutPatientVisitDate());
        found.setOutPatientVisitTime(tbl_outpatient_visit_alarm.getOutPatientVisitTime());
        return outPatientVisitAlarmRepository.save(found);
    }
    @Nullable
    public List<Tbl_medication_alarm> getPillAlarmOn(){
        return medicationAlarmRepository.findByMedicationAlarmOnoffIsTrue();
    }

    @Nullable
    public List<Tbl_outpatient_visit_alarm> getVisitAlarmOn(){
        return outPatientVisitAlarmRepository.findByVisitAlarmIsTrue();
    }
    @Nullable
    public Tbl_medication_alarm getTodayAlarm(String username){
        return medicationAlarmRepository.findById(username).orElse(null);
    }

    @Nullable
    public Tbl_outpatient_visit_alarm getPatientAppointment(String username){
        return outPatientVisitAlarmRepository.findById(username).orElse(null);
    }
    @Nullable
    public List<Tbl_medication_record>getFinishedRecord(String id){
        return medicationRecordRepository.findByPk_IdAndFinishedIsTrue(id);
    }
}