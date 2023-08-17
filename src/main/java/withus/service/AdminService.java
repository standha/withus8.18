package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import withus.dto.*;
import withus.dto.HeaderInfoDTO;
import withus.dto.HelpRequest.CaregiverHelpRequestDTO;
import withus.dto.HelpRequest.PatientHelpRequestDTO;
import withus.dto.wwithus.*;
import withus.entity.*;
import withus.repository.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;

    private final NatriumRecordRepository natriumRecordRepository;

    @Autowired
    public AdminService(UserRepository userRepository, NatriumRecordRepository natriumRecordRepository) {
        this.userRepository = userRepository;
        this.natriumRecordRepository = natriumRecordRepository;
    }

    @Autowired
    private UserRepositorySupport userRepositorySupport;

    @Nullable
    public ArrayList<String> getAllUserPlz() {
        return userRepository.findByAll();
    }

    // * 어드민 페이지
    // 1. Html head 에 포함 되는 정보 DTO
    @Nullable
    public HeaderInfoDTO getHeaderInfo(String userId) {
        return userRepositorySupport.findHeaderInfo(userId);
    }

    // 2. 수분 기록 정보
    @Nullable
    public List<MoistureAvgDTO> getMoistureAvg(String userId) {
        return userRepositorySupport.findMoistureWeek(userId);
    }

    @Nullable
    public List<Tbl_mositrue_record> getMoistureAsc(String userId) {
        return userRepositorySupport.findMoistureAsc(userId);
    }

    // 3.복약기록
    @Nullable
    public List<Tbl_medication_alarm> getPillAsc(String userId) {
        return userRepositorySupport.findPillAsc(userId);
    }


    @Nullable
    public List<HelpRequestDTO> getHelpRequestAsc() {
        return userRepositorySupport.findHelpRequestAsc();
    }

    @Nullable
    public List<PatientHelpRequestDTO> getPatientRequest() {
        return userRepositorySupport.findPatientHelpRequest();
    }

    @Nullable
    public List<CaregiverHelpRequestDTO> getCaregiverRequest() {
        return userRepositorySupport.findCaregiverHelpRequest();
    }
    @Nullable
    public List<Tbl_mindHealth_record> getMindHealth(String userId){return userRepositorySupport.findMindHealth(userId);}
    @Nullable
    public List<Tbl_symptom_log> getSymptom(String userId) {
        return userRepositorySupport.findSymptom(userId);
    }

    @Nullable
    public List<SymptomAvgDTO> getSymptomAvg(String userId) {
        return userRepositorySupport.findSymptomAvg(userId);
    }

    @Nullable
    public List<Tbl_Exercise_record> getExercise(String userId) {
        return userRepositorySupport.findExercise(userId);
    }
    @Nullable
    public List<GoalDTO> getPatientGoal(String userId){
            return userRepositorySupport.findPatientGoal(userId);
    }
    @Nullable
    public List<GoalDTO> getCaregiverGoal(String userId){
        return userRepositorySupport.findCaregiverGoal(userId);
    }

    @Nullable
    public List<ExerciseDTO> getExerciseAvg(String userId) {
        return userRepositorySupport.findExerciseAvg(userId);
    }

    @Nullable
    public List<Tbl_blood_pressure_pulse> getBloodPressure(String userId) {
        return userRepositorySupport.findBloodPressure(userId);
    }

    @Nullable
    public List<WeightAvgDTO> getWeightAvg(String userId) {
        return userRepositorySupport.findWeightAvg(userId);
    }

    @Nullable
    public List<Tbl_weight> getWeightAsc(String userId) {
        return userRepositorySupport.findWeightAsc(userId);
    }

    @Nullable
    public List<Tbl_natrium_record> getNatriumAsc(String userId) {
        return userRepositorySupport.findNatriumAsc(userId);
    }

    @Nullable
    public List<Tbl_patient_duration_time> getPatientDurationTime(String userId){
        return userRepositorySupport.findPatientDurationTimeAsc(userId);
    }

    @Nullable
    public List<Tbl_caregiver_duration_time> getCaregiverDurationTime(String userId){
        return userRepositorySupport.findCaregiverDurationTimeAsc(userId);
    }
    @Nullable
    public List<PatientMainButtonCountSumDTO> getPatientMainButtonCount(String userId) {
        return userRepositorySupport.findPatientMainButtonCountSum(userId);
    }

    @Nullable
    public List<CaregiverMainButtonCountSumDTO> getCaregiverMainButtonCount(String userId){
        return userRepositorySupport.findCaregiverMainButtonCountSum(userId);
    }

    // admin화면 버튼 클릭수
    @Nullable
    public List<Tbl_patient_main_button_count> getPatientMainButtonCountAsc(String userId) {
        return userRepositorySupport.findPatientMainButtonCount(userId);
    }
    @Nullable
    public List<Tbl_patient_sub_button_count> getPatientSubButtonCountAsc(String userId) {
        return userRepositorySupport.findPatientSubButtonCount(userId);
    }
    @Nullable
    public List<Tbl_patient_detail_button_count> getPatientDetailButtonCountAsc(String userId) {
        return userRepositorySupport.findPatientDetailButtonCount(userId);
    }
    @Nullable
    public List<Tbl_caregiver_main_button_count> getCaregiverMainButtonCountAsc(String userId){
        return userRepositorySupport.findCaregiverMainButtonCount(userId);
    }
    @Nullable
    public List<Tbl_caregiver_sub_button_count> getCaregiverSubButtonCountAsc(String userId){
        return userRepositorySupport.findCaregiverSubButtonCount(userId);
    }
    @Nullable
    public List<Tbl_caregiver_detail_button_count> getCaregiverDetailButtonCountAsc(String userId){
        return userRepositorySupport.findCaregiverDetailButtonCount(userId);
    }

    @Nullable
    public List<WwithusHistoryDTO> getWwithusHistory(String userId, User.Type type){
        if(type == User.Type.CAREGIVER){
            return userRepository.findCaregiverWwithusHistory(userId);
        } else {
            return userRepository.findPatientWwithusHistory(userId);
        }
    }

    public List<NatriumCountDTO> getNatriumCountWeek(String userId) {
        Integer week = 1;
        List<Tbl_natrium_record> natrium_records = natriumRecordRepository.findByPk_Id(userId);
        List<NatriumCountDTO> natriumCountList = new ArrayList<>();
        //TriFunction< T , U, S,  R> t(유저의 타트륨 섭취 리스트), u(주차), s(염분섭취), r(결과)
        TriFunction<List<Tbl_natrium_record>, Integer, String, Integer> countFoo = (foo, wk, meal) -> foo.stream().filter(tl -> tl.getWeek().equals(wk)).
                map(tbl -> tbl.getDinner().toString() + tbl.getLunch().toString() + tbl.getMorning().toString())
                .map(s -> StringUtils.countOccurrencesOf(s, meal)).mapToInt(Integer::intValue).sum();

        while (week <= 24) {
            Integer finalWeek = week;
            NatriumCountDTO natriumCountDTO = NatriumCountDTO.builder()
                    .week(week)
                    .noneCount(countFoo.apply(natrium_records, finalWeek, "0"))
                    .lowCount(countFoo.apply(natrium_records, finalWeek, "1"))
                    .mediumCount(countFoo.apply(natrium_records, finalWeek, "2"))
                    .highCount(countFoo.apply(natrium_records, finalWeek, "3"))
                    .build();
            natriumCountList.add(natriumCountDTO);

            week++;
        }

        return natriumCountList;
    }

    @Nullable
    public List<UserCountInfoDTO> getUserCountInfo() {
        List<UserCountInfoDTO> user_count = userRepositorySupport.findUserCountInfo();
        for (UserCountInfoDTO e : user_count) {
            DayOfWeek dayOfWeek = e.getUserCount().getDayOfWeek();
            int dayOfWeekNumber = dayOfWeek.getValue();

        }
        return user_count;
    }

    @Nullable
    public List<UserGenderCountDTO> getUserGenderCountInfo(){
        List<UserGenderCountDTO> user_gender_count = userRepository.findUserGenderCount();
        return user_gender_count;
    }

    public List<UserAgeCountDTO> getUserAgeCountInfo(){
        List<UserAgeCountDTO> user_age_count = userRepository.findUserAgeCount();
        return user_age_count;
    }

    public List<UserRegisterCountDTO> getUserRegisterCountInfo(){
        List<UserRegisterCountDTO> user_register_count = userRepository.findUserRegisterCount();
        return user_register_count;
    }

    public List<UserWeekCountDTO> getUserWeekCountInfo(){
        List<UserWeekCountDTO> user_week_count = userRepository.findUserWeekCount();
        return user_week_count;
    }

    public List<UserRelativeCountDTO> getUserRelativeCountInfo(){
        List<UserRelativeCountDTO> user_relative_count = userRepository.findUserRelativeCount();
        return user_relative_count;
    }

    public List<CaregiverButtonSumDTO> getCaregiverButtonSumInfo(){
        List<CaregiverButtonSumDTO> caregiver_button_sum = userRepositorySupport.findCaregiverButtonSum();
        return caregiver_button_sum;
    }
    public List<PatientButtonSumDTO> getPatientButtonSumInfo() {
        List<PatientButtonSumDTO> patient_button_sum = userRepositorySupport.findPatientButtonSum();
        return patient_button_sum;
    }

    public User.Type getTypeInfo(String userId){
        User.Type type_info = userRepositorySupport.findTypeInfo(userId);
        return type_info;
    }
}

@FunctionalInterface
interface TriFunction<T, U, S, R> {

    /**
     * 기존 Function의 인자가 2개 -> 3개로 변경
     *
     * @param t 1번째 인자 함수 argument
     * @param u 2번째 인자 함수 argument
     * @param s 3번째 인자 함수 argument
     * @return the function result
     */
    R apply(T t, U u, S s);
}
