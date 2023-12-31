package withus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.dto.*;
import withus.dto.HelpRequest.CaregiverHelpRequestDTO;
import withus.dto.HelpRequest.PatientHelpRequestDTO;
import withus.dto.wwithus.CaregiverButtonSumDTO;
import withus.dto.wwithus.PatientButtonSumDTO;
import withus.dto.wwithus.UserCountInfoDTO;
import withus.entity.*;

import java.util.List;

@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final JPAQueryFactory queryFactory;

    public UserRepositorySupport(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    public List<MoistureAvgDTO> findMoistureWeek(String userId) {
        QTbl_mositrue_record mr = QTbl_mositrue_record.tbl_mositrue_record;

        List<MoistureAvgDTO> moistureAvg = queryFactory.select(Projections.constructor(MoistureAvgDTO.class, mr.week, mr.intake.avg()))
                .from(mr)
                .groupBy(mr.week)
                .orderBy(mr.week.asc())
                .where(mr.pk.id.eq(userId))
                .fetch();

        return moistureAvg;
    }




    public List<Tbl_mositrue_record> findMoistureAsc(String userId) {
        QTbl_mositrue_record mr = QTbl_mositrue_record.tbl_mositrue_record;
        List<Tbl_mositrue_record> moistureAsc = queryFactory.selectFrom(mr)
                .where(mr.pk.id.eq(userId))
                .orderBy(mr.week.asc())
                .orderBy(mr.pk.date.asc())
                .fetch();

        return moistureAsc;
    }

    public List<Tbl_symptom_log> findSymptom(String userId) {
        QTbl_symptom_log log = QTbl_symptom_log.tbl_symptom_log;
        List<Tbl_symptom_log> symptom = queryFactory.selectFrom(log)
                .where(log.pk.id.eq(userId))
                .orderBy(log.week.asc())
                .orderBy(log.pk.date.asc())
                .fetch();

        return symptom;
    }

    public List<SymptomAvgDTO> findSymptomAvg(String userId) {
        QTbl_symptom_log log = QTbl_symptom_log.tbl_symptom_log;
        List<SymptomAvgDTO> symptomAvg = queryFactory.select(Projections.constructor(SymptomAvgDTO.class,
                log.week, log.outofbreath.sum(), log.tired.sum(), log.ankle.sum(), log.cough.sum()))
                .from(log)
                .groupBy(log.week)
                .orderBy(log.week.asc())
                .where(log.pk.id.eq(userId))
                .fetch();

        return symptomAvg;
    }

    public HeaderInfoDTO findHeaderInfo(String userId) {
        QUser user = QUser.user;
        QTbl_goal goal = QTbl_goal.tbl_goal;

        HeaderInfoDTO headerInfo = queryFactory.select(Projections.constructor(HeaderInfoDTO.class, user.name, user.userId, user.birthdate, goal.goal, user.level))
                .from(user)
                .leftJoin(goal).on(user.userId.eq(goal.goalId))
                .where(user.userId.eq(userId))
                .fetchFirst();

        return headerInfo;
    }

    public User.Type findTypeInfo(String userId) {
        QUser user = QUser.user;

        User.Type typeString = queryFactory.select(user.type)
                .from(user)
                .where(user.userId.eq(userId))
                .fetchFirst();

        return typeString;
    }

    public List<UserCountInfoDTO> findUserCountInfo() {
        QUser user = QUser.user;

        List<UserCountInfoDTO> userCountInfo = queryFactory.select(Projections.constructor(UserCountInfoDTO.class, user.registrationDateTime))
                .from(user)
                .orderBy(user.registrationDateTime.asc())
                .fetch();
        return userCountInfo;
    }




    public List<Tbl_medication_alarm> findPillAsc(String userId) {
        QTbl_medication_alarm mr = QTbl_medication_alarm.tbl_medication_alarm;
        List<Tbl_medication_alarm> pillAsc = queryFactory.selectFrom(mr)
                .where(mr.pk.id.eq(userId))
                .orderBy(mr.week.asc())
                .orderBy(mr.pk.date.asc())
                .fetch();

        return pillAsc;
    }

    public List<GoalDTO> findPatientGoal(String userId){
        QTbl_patient_seed_day seedDay= QTbl_patient_seed_day.tbl_patient_seed_day;

        List<GoalDTO> goalList = queryFactory.select(Projections.constructor(GoalDTO.class,seedDay.pk.id, seedDay.week, seedDay.topGoal, seedDay.middleGoal, seedDay.bottomGoal, seedDay.pk.date))
                .from(seedDay)
                .orderBy(seedDay.week.asc())
                .orderBy(seedDay.pk.date.asc())
                .where(seedDay.pk.id.eq(userId))
                .fetch();
        return goalList;
    }

    public List<GoalDTO> findCaregiverGoal(String userId){
        QTbl_caregiver_seed_day seedDay= QTbl_caregiver_seed_day.tbl_caregiver_seed_day;
        List<GoalDTO> goalList = queryFactory.select(Projections.constructor(GoalDTO.class,seedDay.pk.id, seedDay.week, seedDay.topGoal, seedDay.middleGoal, seedDay.bottomGoal, seedDay.pk.date))
                .from(seedDay)
                .orderBy(seedDay.week.asc())
                .orderBy(seedDay.pk.date.asc())
                .where(seedDay.pk.id.eq(userId))
                .fetch();
        return goalList;
    }

    public List<ExerciseDTO> findExerciseAvg(String userId) {
        QTbl_Exercise_record er = QTbl_Exercise_record.tbl_Exercise_record;
        List<ExerciseDTO> exerciseAvg = queryFactory.select(Projections.constructor(ExerciseDTO.class, er.week, er.walking.count(), er.cycling.count(), er.swimming.count(), er.strength.count()))
                .from(er)
                .groupBy(er.week)
                .orderBy(er.week.asc())
                .where(er.week.eq(er.week))
                .where(er.pk.id.eq(userId))
                .fetch();

        return exerciseAvg;
    }

    public List<Tbl_Exercise_record> findExercise(String userId) {
        QTbl_Exercise_record er = QTbl_Exercise_record.tbl_Exercise_record;
        List<Tbl_Exercise_record> exerciseRecords = queryFactory.selectFrom(er)
                .where(er.pk.id.eq(userId))
                .orderBy(er.week.asc())
                .orderBy(er.pk.date.asc())
                .fetch();

        for (Tbl_Exercise_record data : exerciseRecords) {
            logger.info("[{}] userId:{}, exerciseRecords:{}, week:{}", Thread.currentThread().getStackTrace()[1].getMethodName(), userId, data.getHour() + "-" + data.getMinute(), data.getWeek());
        }

        return exerciseRecords;
    }

    public List<Tbl_blood_pressure_pulse> findBloodPressure(String userId) {
        QTbl_blood_pressure_pulse br = QTbl_blood_pressure_pulse.tbl_blood_pressure_pulse;
        List<Tbl_blood_pressure_pulse> blood_pressure_pulses = queryFactory.selectFrom(br)
                .where(br.pk.id.eq(userId))
                .orderBy(br.week.asc())
                .orderBy(br.pk.date.asc())
                .fetch();

        return blood_pressure_pulses;
    }
    public List<Tbl_mindHealth_record> findMindHealth(String userId){
        QTbl_mindHealth_record mr = QTbl_mindHealth_record.tbl_mindHealth_record;
        List<Tbl_mindHealth_record> mindHealthRecords = queryFactory.selectFrom(mr)
                .where(mr.pk.id.eq(userId))
                .orderBy(mr.week.asc())
                .orderBy(mr.pk.date.asc())
                .fetch();
        return mindHealthRecords;
    }

    public List<HelpRequestDTO> findHelpRequestAsc() {
        QWithusHelpRequest wr = QWithusHelpRequest.withusHelpRequest;
        QUser u = QUser.user;
        QUser c = QUser.user.caregiver;

        List<HelpRequestDTO> requestAsc = queryFactory.select(Projections.constructor(HelpRequestDTO.class, wr.dateTime, u.name,
                u.userId, u.contact, c.contact, wr.helpCode))
                .from(wr)
                .leftJoin(u).on(wr.user.userId.eq(u.userId))
                .leftJoin(c).on(u.caregiver.contact.eq(c.contact))
                .orderBy(wr.dateTime.asc())
                .fetch();

        return requestAsc;
    }

    public List<PatientHelpRequestDTO> findPatientHelpRequest() {
        QTbl_helper_request hp = QTbl_helper_request.tbl_helper_request;
        QUser u = QUser.user;
        QUser c = QUser.user.caregiver;
        List<PatientHelpRequestDTO> requestPatient = queryFactory.select(Projections.constructor(PatientHelpRequestDTO.class,
                u.name, u.userId, u.contact, c.contact, hp.pk.date, hp.pk.time))
                .from(hp)
                .where(u.type.eq(User.Type.PATIENT))
                .leftJoin(u).on(hp.pk.id.eq(u.userId))
                .leftJoin(c).on(u.caregiver.contact.eq(c.contact))
                .orderBy(hp.pk.date.desc())
                .orderBy(hp.pk.time.desc())
                .fetch();

        return requestPatient;
    }

    public List<CaregiverHelpRequestDTO> findCaregiverHelpRequest() {
        QTbl_helper_request hp = QTbl_helper_request.tbl_helper_request;
        QUser user = QUser.user;

        List<CaregiverHelpRequestDTO> requestCaregiver = queryFactory.select(Projections.constructor(CaregiverHelpRequestDTO.class,
                user.caregiver.name, user.caregiver.userId, user.caregiver.contact, user.name, user.userId, user.contact, hp.pk.date, hp.pk.time))
                .from(user)
                .where(user.caregiver.type.eq(User.Type.CAREGIVER))
                .leftJoin(hp).on(hp.pk.id.eq(user.caregiver.userId))
                .orderBy(hp.pk.date.desc())
                .orderBy(hp.pk.time.desc())
                .where(hp.pk.id.isNotNull())
                .fetch();

        return requestCaregiver;
    }

    public List<WeightAvgDTO> findWeightAvg(String userId) {
        QTbl_weight tw = QTbl_weight.tbl_weight;

        List<WeightAvgDTO> weightAvgList = queryFactory.select(Projections.constructor(WeightAvgDTO.class,
                tw.week, tw.weight.avg()))
                .from(tw)
                .where(tw.pk.id.eq(userId))
                .groupBy(tw.week)
                .orderBy(tw.week.asc())
                .fetch();

        return weightAvgList;
    }

    public List<Tbl_weight> findWeightAsc(String userId) {
        QTbl_weight tw = QTbl_weight.tbl_weight;

        List<Tbl_weight> weightsAscList = queryFactory.selectFrom(tw)
                .where(tw.pk.id.eq(userId))
                .orderBy(tw.week.asc())
                .orderBy(tw.pk.date.asc())
                .fetch();

        return weightsAscList;
    }

    public List<Tbl_natrium_record> findNatriumAsc(String userId) {
        QTbl_natrium_record nr = QTbl_natrium_record.tbl_natrium_record;

        List<Tbl_natrium_record> natriumAscList = queryFactory.selectFrom(nr)
                .where(nr.pk.id.eq(userId))
                .orderBy(nr.week.asc())
                .orderBy(nr.pk.date.asc())
                .fetch();

        return natriumAscList;
    }


    public Integer findPatientDaySeedSum(String userId){
        QTbl_patient_seed_day seedDay = QTbl_patient_seed_day.tbl_patient_seed_day;
        Integer sumDaySeed = queryFactory.select(
                        seedDay.bloodPressure.sum()
                                .add(seedDay.exercise.sum())
                                .add(seedDay.medicine.sum())
                                .add(seedDay.natirumMoisture.sum())
                                .add(seedDay.mindDiary.sum())
                                .add(seedDay.mindScore.sum())
                                .add(seedDay.symptom.sum())
                                .add(seedDay.waterIntake.sum())
                                .add(seedDay.weight.sum())
                                .as("totalSum")
                )
                .from(seedDay)
                .where(seedDay.pk.id.eq(userId))
                .fetchOne();

        return (sumDaySeed == null ? 0: sumDaySeed);
    }

    public Integer findCaregiverDaySeedSum(String userId){
        QTbl_caregiver_seed_day seedDay = QTbl_caregiver_seed_day.tbl_caregiver_seed_day;
        Integer sumDaySeed = queryFactory.select(
                        seedDay.bloodPressure.sum()
                                .add(seedDay.exercise.sum())
                                .add(seedDay.medicine.sum())
                                .add(seedDay.dietManagement.sum())
                                .add(seedDay.mindDiary.sum())
                                .add(seedDay.mindScore.sum())
                                .add(seedDay.weight.sum())
                                .as("totalSum")
                )
                .from(seedDay)
                .where(seedDay.pk.id.eq(userId))
                .fetchOne();

        return (sumDaySeed == null ? 0: sumDaySeed);
    }
    public Integer findWeekSeedSum(String userId){
        QTbl_seed_week seedWeek = QTbl_seed_week.tbl_seed_week;
        QUser user = QUser.user;
        Integer sumGoalSeed = queryFactory.select(
                        seedWeek.goal.sum()
                )
                .from(seedWeek)
                .where(seedWeek.key.id.eq(userId))
                .fetchOne();
        Integer sumLevel = queryFactory.select(
                        user.level.divide(4)
                )
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne();

        return (sumGoalSeed == null ? 0: sumGoalSeed * 10) + (sumLevel == null ? 0: sumLevel * 20);
    }


    public List<PatientMainButtonCountSumDTO> findPatientMainButtonCountSum(String userId) {
        QTbl_patient_main_button_count bc = QTbl_patient_main_button_count.tbl_patient_main_button_count;

        List<PatientMainButtonCountSumDTO> buttonCountSum = queryFactory.select(Projections.constructor(PatientMainButtonCountSumDTO.class,
                bc.alarm.sum(), bc.bloodPressure.sum(), bc.diseaseInfo.sum(), bc.exercise.sum(), bc.goal.sum(), bc.helper.sum(), bc.level.sum(), bc.natriumMoisture.sum(), bc.symptom.sum(),
                bc.weight.sum(), bc.withusRang.sum(), bc.medicine.sum(), bc.mindHealth.sum(),  bc.board.sum()))
                .from(bc)
                .where(bc.key.id.eq(userId))
                .fetch();

        return buttonCountSum;
    }

    public List<CaregiverMainButtonCountSumDTO> findCaregiverMainButtonCountSum(String userId) {
        QTbl_caregiver_main_button_count bc = QTbl_caregiver_main_button_count.tbl_caregiver_main_button_count;

        List<CaregiverMainButtonCountSumDTO> buttonCountSum = queryFactory.select(Projections.constructor(CaregiverMainButtonCountSumDTO.class,
                        bc.goal.sum(), bc.level.sum(), bc.withusRang.sum(), bc.diseaseInfo.sum(), bc.helper.sum(), bc.medicine.sum(), bc.bloodPressure.sum(), bc.exercise.sum(),
                        bc.familyObservation.sum(), bc.dietManagement.sum(), bc.weight.sum(), bc.mindHealth.sum(), bc.alarm.sum(), bc.board.sum()))
                .from(bc)
                .where(bc.key.id.eq(userId))
                .fetch();

        return buttonCountSum;
    }


    //admin button_count
    public List<Tbl_patient_main_button_count> findPatientMainButtonCount(String userId) {
        QTbl_patient_main_button_count bc = QTbl_patient_main_button_count.tbl_patient_main_button_count;

        List<Tbl_patient_main_button_count> buttonCount = queryFactory.selectFrom(bc)
                .where(bc.key.id.eq(userId))
                .orderBy(bc.key.week.asc())
                .fetch();

        return buttonCount;
    }
    public List<Tbl_patient_sub_button_count> findPatientSubButtonCount(String userId) {
        QTbl_patient_sub_button_count bc = QTbl_patient_sub_button_count.tbl_patient_sub_button_count;

        List<Tbl_patient_sub_button_count> buttonCount = queryFactory.selectFrom(bc)
                .where(bc.key.id.eq(userId))
                .orderBy(bc.key.week.asc())
                .fetch();

        return buttonCount;
    }
    public List<Tbl_patient_detail_button_count> findPatientDetailButtonCount(String userId) {
        QTbl_patient_detail_button_count bc = QTbl_patient_detail_button_count.tbl_patient_detail_button_count;

        List<Tbl_patient_detail_button_count> buttonCount = queryFactory.selectFrom(bc)
                .where(bc.key.id.eq(userId))
                .orderBy(bc.key.week.asc())
                .fetch();

        return buttonCount;
    }
    public List<Tbl_caregiver_main_button_count> findCaregiverMainButtonCount(String userId) {
        QTbl_caregiver_main_button_count bc = QTbl_caregiver_main_button_count.tbl_caregiver_main_button_count;

        List<Tbl_caregiver_main_button_count> buttonCount = queryFactory.selectFrom(bc)
                .where(bc.key.id.eq(userId))
                .orderBy(bc.key.week.asc())
                .fetch();
        return buttonCount;
    }
    public List<Tbl_caregiver_sub_button_count> findCaregiverSubButtonCount(String userId) {
        QTbl_caregiver_sub_button_count bc = QTbl_caregiver_sub_button_count.tbl_caregiver_sub_button_count;

        List<Tbl_caregiver_sub_button_count> buttonCount = queryFactory.selectFrom(bc)
                .where(bc.key.id.eq(userId))
                .orderBy(bc.key.week.asc())
                .fetch();
        return buttonCount;
    }
    public List<Tbl_caregiver_detail_button_count> findCaregiverDetailButtonCount(String userId) {
        QTbl_caregiver_detail_button_count bc = QTbl_caregiver_detail_button_count.tbl_caregiver_detail_button_count;

        List<Tbl_caregiver_detail_button_count> buttonCount = queryFactory.selectFrom(bc)
                .where(bc.key.id.eq(userId))
                .orderBy(bc.key.week.asc())
                .fetch();
        return buttonCount;
    }
    public List<PatientButtonSumDTO> findPatientButtonSum(){
        QTbl_patient_main_button_count bc = QTbl_patient_main_button_count.tbl_patient_main_button_count;
        List<PatientButtonSumDTO> buttonCount = queryFactory.select(Projections.constructor(PatientButtonSumDTO.class,
                bc.goal.sum(), bc.level.sum(), bc.medicine.sum(), bc.bloodPressure.sum(), bc.exercise.sum(), bc.weight.sum(), bc.mindHealth.sum(),
                bc.board.sum(), bc.alarm.sum(), bc.symptom.sum(), bc.natriumMoisture.sum(), bc.withusRang.sum(), bc.diseaseInfo.sum(),
                bc.helper.sum(), bc.infoEdit.sum()))
                .from(bc)
                .fetch();
        return buttonCount;
    }
    public List<CaregiverButtonSumDTO> findCaregiverButtonSum(){
        QTbl_caregiver_main_button_count bc = QTbl_caregiver_main_button_count.tbl_caregiver_main_button_count;

        List<CaregiverButtonSumDTO> buttonCount = queryFactory.select(Projections.constructor(CaregiverButtonSumDTO.class,
                bc.goal.sum(), bc.level.sum(), bc.medicine.sum(), bc.bloodPressure.sum(), bc.exercise.sum(), bc.weight.sum(), bc.mindHealth.sum(),
                bc.board.sum(), bc.alarm.sum(), bc.familyObservation.sum(), bc.dietManagement.sum(), bc.withusRang.sum(), bc.diseaseInfo.sum(),
                bc.helper.sum(), bc.infoEdit.sum()))
                .from(bc)
                .fetch();
        return buttonCount;
    }

    public List<Tbl_caregiver_duration_time> findCaregiverDurationTimeAsc(String userId) {
        QTbl_caregiver_duration_time dt = QTbl_caregiver_duration_time.tbl_caregiver_duration_time;
        List<Tbl_caregiver_duration_time> dtAsc = queryFactory.selectFrom(dt)
                .where(dt.key.id.eq(userId))
                .orderBy(dt.key.week.asc())
                .fetch();

        return dtAsc;
    }
    public List<Tbl_patient_duration_time> findPatientDurationTimeAsc(String userId) {
        QTbl_patient_duration_time dt = QTbl_patient_duration_time.tbl_patient_duration_time;
        List<Tbl_patient_duration_time> dtAsc = queryFactory.selectFrom(dt)
                .where(dt.key.id.eq(userId))
                .orderBy(dt.key.week.asc())
                .fetch();
        return dtAsc;
    }

    @Transactional
    public void updateCaregiver(User caregiver){
        QUser user = QUser.user;

        queryFactory
                .update(user)
                .set(user.caregiver.contact, caregiver.getCaregiver().getContact())
                .where(user.contact.eq(caregiver.getContact()))
                .execute();

    }
    @Transactional
    public void updateTempContact(User user){
        QUser u = QUser.user;

        queryFactory
                .update(u)
                .set(u.tempContact, user.getTempContact())
                .where(u.contact.eq(user.getContact()))
                .execute();

    }

}

