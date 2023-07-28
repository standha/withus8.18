package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_medication_record;
import withus.repository.MedicationAlarmRepository;
import withus.repository.MedicationRecordRepository;

@Service
public class MedicationService {
    private final MedicationAlarmRepository medicationAlarmRepository;
//    private final MedicationRecordRepository medicationRecordRepository;
    @Autowired
    public MedicationService(MedicationAlarmRepository medicationAlarmRepository){
        this.medicationAlarmRepository = medicationAlarmRepository;
//        this.medicationRecordRepository = medicationRecordRepository;
    }

    @NonNull
    public Tbl_medication_alarm upsertMedicationCheck(Tbl_medication_alarm tbl_medication_alarm) {
        Tbl_medication_alarm found = medicationAlarmRepository.findById(tbl_medication_alarm.getId()).orElse(null);
//        Tbl_medication_alarm find = medicationAlarmRepository.findById(username).orElse(null);

        found.setMorning(tbl_medication_alarm.getMorning());
        found.setLunch(tbl_medication_alarm.getLunch());
        found.setDinner(tbl_medication_alarm.getDinner());

        Tbl_medication_alarm saved = medicationAlarmRepository.save(tbl_medication_alarm);

        return saved;

    }

//    @NonNull
//    public String getMorningValue(RecordKey pk) {
//        Tbl_medication_alarm record = medicationAlarmRepository.getOne()
//        return (record != null) ? record.getMorning() : "0";
//    }
//
//    @NonNull
//    public String getLunchValue(RecordKey pk) {
//        Tbl_medication_record record = medicationRecordRepository.findByPk(pk).orElse(null);
//        return (record != null) ? record.getLunch() : "0";
//    }
//
//    @NonNull
//    public String getDinnerValue(RecordKey pk) {
//        Tbl_medication_record record = medicationRecordRepository.findByPk(pk).orElse(null);
//        return (record != null) ? record.getDinner() : "0";
//    }
//
    @NonNull
    public Tbl_medication_alarm getFinshedIntake(String username){
        return medicationAlarmRepository.findById(username).orElse(null);

    }




}
