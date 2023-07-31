package withus.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.*;
import withus.repository.MedicationAlarmRepository;
import withus.repository.OutPatientVisitAlarmRepository;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class MedicationAlarmService {
    private final MedicationAlarmRepository medicationAlarmRepository;
    private final OutPatientVisitAlarmRepository outPatientVisitAlarmRepository;

    @Autowired
    public MedicationAlarmService(MedicationAlarmRepository medicationAlarmRepository, OutPatientVisitAlarmRepository outPatientVisitAlarmRepository) {
        this.medicationAlarmRepository = medicationAlarmRepository;
        this.outPatientVisitAlarmRepository = outPatientVisitAlarmRepository;
    }
//    @Nonnull
//    public Tbl_medication_record upsertTrueRecord(Tbl_medication_record tbl_medication_record) {
//        Tbl_medication_record saved = medicationRecordRepository.save(tbl_medication_record);
//
//        return saved;
//    }


    @NonNull
    public Tbl_medication_alarm upsertMedication(Tbl_medication_alarm tbl_medication_alarm) {
//        Tbl_medication_alarm found = medicationAlarmRepository.save(tbl_medication_alarm);
//        found.setMedicationTimeMorning(tbl_medication_alarm.getMedicationTimeMorning());
//        found.setMedicationTimeLunch(tbl_medication_alarm.getMedicationTimeLunch());
//        found.setMedicationTimeDinner(tbl_medication_alarm.getMedicationTimeDinner());
//
//        found.setAlarmOnoffMorning(tbl_medication_alarm.isAlarmOnoffMorning());
//        found.setAlarmOnoffLunch(tbl_medication_alarm.isAlarmOnoffLunch());
//        found.setAlarmOnoffDinner(tbl_medication_alarm.isAlarmOnoffDinner());

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

//
//    @Nullable
//    public List<Tbl_medication_alarm> getPillAlarmOn() {
//        return medicationAlarmRepository.findByMedicationAlarmOnoffIsTrue();
//    }

    @Nullable
    public List<Tbl_outpatient_visit_alarm> getVisitAlarmOn() {
        return outPatientVisitAlarmRepository.findByVisitAlarmIsTrue();
    }

//    @Nullable
//    public Tbl_medication_alarm getTodayAlarm(String username) {
//        return medicationAlarmRepository.findById(username).orElse(null);
//    }

    @Nullable
    public Tbl_outpatient_visit_alarm getPatientAppointment(String username) {
        return outPatientVisitAlarmRepository.findById(username).orElse(null);
    }

//    @Nullable
//    public List<Tbl_medication_record> getFinishedRecord(String id) {
//        return medicationRecordRepository.findByPk_IdAndFinishedIsTrue(id);
//    }

    @Nullable
    public Tbl_medication_alarm getMedication(RecordKey pk) {
        return medicationAlarmRepository.findByPk(pk).orElse(null);
    }

    @Nullable
    public List<Tbl_medication_alarm> getALlMedicationRecord(String id) {
        return medicationAlarmRepository.findByPk_Id(id);
    }



    public int transformTime(int hour) {
        if (hour >= 12) {
            return 1;
        } else {
            return 0;
        }
    }

    public String transformHour(int hour) {
        if (hour > 12) {
            hour = hour - 12;
            String transHour = Integer.toString(hour);
            if (hour >= 10) {
                return transHour;
            } else {
                return "0" + transHour;
            }
        } else {
            if (hour == 0) {
                hour = hour + 12;
                String transHour = Integer.toString(hour);
                return transHour;
            } else {
                String transHour = Integer.toString(hour);
                if (hour >= 10) {
                    return transHour;
                } else {
                    return "0" + transHour;
                }
            }
        }
    }

    public String transformMinute(int minute) {
        if (minute >= 10) {
            String transMinute = Integer.toString(minute);

            return transMinute;
        } else {
            String transMinute = Integer.toString(minute);

            return "0" + transMinute;
        }
    }
}