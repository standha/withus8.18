package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import withus.auth.NoOpPasswordEncoder;
import withus.board.DataNotFoundException;
import withus.dto.MoistureAvgDTO;
import withus.entity.*;
import withus.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MedicationAlarmRepository medicationAlarmRepository;
    private final OutPatientVisitAlarmRepository outPatientVisitAlarmRepository;

    private final GoalRepository goalRepositroy;
//    private final GoalRepository1 goalRepositroy1;
//    private final GoalRepository2 goalRepositroy2;
//    private final GoalRepository3 goalRepositroy3;

    private final PatientMainCountRepository patientMainCountRepository;
    private final PatientSubCountRepository patientSubCountRepository;
    private final PatientDetailCountRepository patientDetailCountRepository;

    private final CaregiverMainCountRepository caregiverMainCountRepository;
    private final CaregiverSubCountRepository caregiverSubCountRepository;
    private final CaregiverDetailCountRepository caregiverDetailCountRepository;

    @Autowired
    public UserService(UserRepository userRepository, MedicationAlarmRepository medicationAlarmRepository, OutPatientVisitAlarmRepository outPatientVisitAlarmRepository,
                       GoalRepository goalRepositroy, PatientMainCountRepository patientMainCountRepository, PatientSubCountRepository patientSubCountRepository, PatientDetailCountRepository patientDetailCountRepository,
                        CaregiverMainCountRepository caregiverMainCountRepository, CaregiverSubCountRepository caregiverSubCountRepository, CaregiverDetailCountRepository caregiverDetailCountRepository ) {
        this.userRepository = userRepository;
        this.goalRepositroy = goalRepositroy;
//        this.goalRepositroy1 = goalRepositroy1;
//        this.goalRepositroy2 = goalRepositroy2;
//        this.goalRepositroy3 = goalRepositroy3;
        this.medicationAlarmRepository = medicationAlarmRepository;
        this.outPatientVisitAlarmRepository = outPatientVisitAlarmRepository;
        this.patientMainCountRepository = patientMainCountRepository;
        this.patientSubCountRepository = patientSubCountRepository;
        this.patientDetailCountRepository = patientDetailCountRepository;
        this.caregiverMainCountRepository = caregiverMainCountRepository;
        this.caregiverSubCountRepository = caregiverSubCountRepository;
        this.caregiverDetailCountRepository = caregiverDetailCountRepository;
    }

    @Autowired
    private UserRepositorySupport userRepositorySupport;

//    @Override
//    @NonNull
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUserId(username).orElseThrow(() -> {
//                    String message = String.format("Username \"%s\" does not exist!", username);
//                    return new UsernameNotFoundException(message);
//                }
//        );
//    }

    @Override
    // loadUserByUsername 메서드는 사용자명으로 비밀번호를 조회하여 리턴하는 메서드
    // -> SecurityConfig
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> _user = this.userRepository.findById(username);
        if (!_user.isPresent()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        User user = _user.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("withusad".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


    @Nullable
    public User getUserByIdAndDate(String id) {
        User user = userRepository.findByUserId(id).orElse(null);
        user.setUserRecordDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        userRepository.saveAndFlush(user);

        return user;
    }

    @Nullable
    public User getUserById(String id) {
        User user = userRepository.findByUserId(id).orElse(null);
        return user;
    }


    @Nullable
    public User getUserByCaregiverId(String caregiverId) {
        return userRepository.findByCaregiverUserId(caregiverId).orElse(null);
    }
    @Nullable
    public User getUserTopTempContact(String tempContact){
        return userRepository.findTopByTempContact(tempContact).orElse(null);
    }

    @Nullable
    public User getUserByContact(String contact) {
        return userRepository.findByContact(contact).orElse(null);
    }

    @Nullable
    public User getUserTopByCaregiverContact(String caregiverContact) {return userRepository.findTopByCaregiverContact(caregiverContact).orElse(null);}
    @NonNull
    public User upsertUser(User user) {
        return userRepository.save(user);
    }

    @NonNull
    public User upsertUserEncodingPassword(User user) {
        String plaintextPassword = user.getPassword();
        NoOpPasswordEncoder noOpPasswordEncoder = NoOpPasswordEncoder.getInstance();
        String encodedPassword = noOpPasswordEncoder.encode(plaintextPassword);
        User saved = userRepository.save(user);
        String checkType = user.getType().name();


        Tbl_goal tbl_goal = Tbl_goal.builder()
                .goalId(saved.getUserId())
                .goal(0)
                .top_goals(null)
                .middle_goals(null)
                .bottom_goals(null)
                .build();

//        Tbl_topgoals tbl_topgoals = Tbl_topgoals.builder()
//                .goalId(saved.getUserId())
//                .top_goals(null)
//                .build();
//
//        Tbl_middlegoals tbl_middlegoals = Tbl_middlegoals.builder()
//                .goalId(saved.getUserId())
//                .middle_goals(null)
//                .build();
//
//        Tbl_bottomgoals tbl_bottomgoals = Tbl_bottomgoals.builder()
//                .goalId(saved.getUserId())
//                .bottom_goals(null)
//                .build();

        goalRepositroy.save(tbl_goal);
//        goalRepositroy1.save(tbl_topgoals);
//        goalRepositroy2.save(tbl_middlegoals);
//        goalRepositroy3.save(tbl_bottomgoals);

        if (checkType == "PATIENT") {
            Tbl_medication_alarm tbl_medication_alarm = Tbl_medication_alarm.builder()
                    .pk(new RecordKey(user.getUserId(), LocalDate.now()))
                    .medicationTimeMorning(null)
                    .medicationTimeLunch(null)
                    .medicationTimeDinner(null)
                    .alarmOnoffMorning(true)
                    .alarmOnoffLunch(true)
                    .alarmOnoffDinner(true)
                    .morning(null)
                    .lunch(null)
                    .dinner(null)
                    .build();

            medicationAlarmRepository.save(tbl_medication_alarm);

            Tbl_outpatient_visit_alarm tbl_outpatient_visit_alarm = Tbl_outpatient_visit_alarm.builder()
                    .id(saved.getUserId())
                    .visitAlarm(false)
                    .build();

            outPatientVisitAlarmRepository.save(tbl_outpatient_visit_alarm);

//            Tbl_goal tbl_goal = Tbl_goal.builder()
//                    .goalId(saved.getUserId())
//                    .goal(0)
//                    //.goal(Integer.toString(0))
//                    .build();

            goalRepositroy.save(tbl_goal);

            for (int i = 1; i <= 24; i++) {
                ProgressKey key = new ProgressKey(saved.getUserId(), i);
                Tbl_patient_main_button_count tbl_button_count = Tbl_patient_main_button_count.builder()
                        .key(key)
                        .goal(0)
                        .level(0)
                        .withusRang(0)
                        .diseaseInfo(0)
                        .helper(0)
                        .medicine(0)
                        .bloodPressure(0)
                        .exercise(0)
                        .symptom(0)
                        .natriumMoisture(0)
                        .weight(0)
                        .mindHealth(0)
                        .board(0)
                        .alarm(0)
                        .infoEdit(0)
                        .build();
                patientMainCountRepository.save(tbl_button_count);
            }
            for (int i = 1; i <= 24; i++) {
                ProgressKey key = new ProgressKey(saved.getUserId(), i);
                Tbl_patient_sub_button_count tbl_sub_button_count = Tbl_patient_sub_button_count.builder()
                        .key(key)
                        .lowLevel(0)
                        .middleLevel(0)
                        .highLevel(0)
                        .makeMyGoal(0)
                        .natriumMoisture(0)
                        .waterIntake(0)
                        .mindManagement(0)
                        .waterIntake(0)
                        .mindDiary(0)
                        .mindScore(0)
                        .mindManagement(0)
                        .hof(0)
                        .notice(0)
                        .question(0)
                        .share(0)
                        .medicineTime(0)
                        .outPatientVisitTime(0)
                        .build();
                patientSubCountRepository.save(tbl_sub_button_count);
            }

            for (int i = 1; i <= 24; i++) {
                ProgressKey key = new ProgressKey(saved.getUserId(), i);
                Tbl_patient_detail_button_count tbl_detail_button_count = Tbl_patient_detail_button_count.builder()
                        .key(key)
                        .recommendDiet(0)
                        .meditation(0)
                        .bodyActivity(0)
                        .deepBreath(0)
                        .consulting(0)
                        .medicineAlarm(0)
                        .bloodPressureAlarm(0)
                        .exerciseAlarm(0)
                        .symptomAlarm(0)
                        .natriumMoistureAlarm(0)
                        .waterIntakeAlarm(0)
                        .weightAlarm(0)
                        .mindScoreAlarm(0)
                        .build();

                patientDetailCountRepository.save(tbl_detail_button_count);
            }


        } else if(checkType == "CAREGIVER"){

            for (int i = 1; i <= 24; i++) {
                CaregiverProgressKey key = new CaregiverProgressKey(saved.getUserId(), i);
                Tbl_caregiver_main_button_count tbl_button_count = Tbl_caregiver_main_button_count.builder()
                        .key(key)
                        .goal(0)
                        .level(0)
                        .withusRang(0)
                        .diseaseInfo(0)
                        .helper(0)
                        .medicine(0)
                        .bloodPressure(0)
                        .exercise(0)
                        .familyObservation(0)
                        .dietManagement(0)
                        .weight(0)
                        .mindHealth(0)
                        .board(0)
                        .alarm(0)
                        .infoEdit(0)
                        .build();
                caregiverMainCountRepository.save(tbl_button_count);
            }
            for (int i = 1; i <= 24; i++) {
                CaregiverProgressKey key = new CaregiverProgressKey(saved.getUserId(), i);
                Tbl_caregiver_sub_button_count tbl_sub_button_count = Tbl_caregiver_sub_button_count.builder()
                        .key(key)
                        .lowLevel(0)
                        .middleLevel(0)
                        .highLevel(0)
                        .makeMyGoal(0)
                        .medicine(0)
                        .bloodPressure(0)
                        .symptom(0)
                        .exercise(0)
                        .natriumMoisture(0)
                        .weight(0)
                        .mindHealth(0)
                        .mindDiary(0)
                        .mindScore(0)
                        .mindManagement(0)
                        .hof(0)
                        .notice(0)
                        .question(0)
                        .share(0)
                        .medicineTime(0)
                        .outPatientVisitTime(0)
                        .build();
                caregiverSubCountRepository.save(tbl_sub_button_count);
            }

            for (int i = 1; i <= 24; i++) {
                CaregiverProgressKey key = new CaregiverProgressKey(saved.getUserId(), i);
                Tbl_caregiver_detail_button_count tbl_detail_button_count = Tbl_caregiver_detail_button_count.builder()
                        .key(key)
                        .recommendDiet(0)
                        .meditation(0)
                        .bodyActivity(0)
                        .deepBreath(0)
                        .consulting(0)
                        .medicineAlarm(0)
                        .bloodPressureAlarm(0)
                        .exerciseAlarm(0)
                        .symptomAlarm(0)
                        .natriumMoistureAlarm(0)
                        .waterIntakeAlarm(0)
                        .weightAlarm(0)
                        .mindDiaryAlarm(0)
                        .mindScoreAlarm(0)
                        .build();
                caregiverDetailCountRepository.save(tbl_detail_button_count);
            }
        }

        return saved;
    }

    public void updateCaregiverInfo(User user){
        userRepositorySupport.updateCaregiver(user);
    }
    public void updateTempContactInfo(User user){
        userRepositorySupport.updateTempContact(user);
    }


    public List<User> getAllPatient() {
        return userRepository.findAll();
    }

    @Nullable
    public List<User> getAllToken() {
        return userRepository.findByAppTokenIsNotNull();
    }

    @Nullable
    public List<User> getPatient(User.Type type) {
        return userRepository.findByType(type);
    }

    @Nullable
    public List<User> getPatientLimit(User.Type type, int limit_week, int limit_level) {
        return userRepository.findByTypeAndWeekLessThanAndLevelLessThan(type, limit_week, limit_level);
    }


    @Nullable
    public List<MoistureAvgDTO> getMoisture(String userId) {
        return userRepositorySupport.findMoistureWeek(userId);
    }

    @Nullable
    public ArrayList<String> getAllUserPlz() {
        return userRepository.findByAll();
    }

    @Nullable
    public ArrayList<String> getAllUserCaregiver(){return userRepository.findByAllCaregiver();}
    @Nullable
    public List<User> getPatientToken(User.Type type) {
        return userRepository.findByAppTokenIsNotNullAndType(type);
    }

    public User getUserForBoard(String userId) {
        Optional<User> user = this.userRepository.findByUserId(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("userId not found");
        }
    }

    public List<User> getUserForRank() {
        return this.userRepository.findAllByOrderByLevelDesc();
    }

    public List<User> getListByType(User.Type type) {
        return this.userRepository.findByTypeOrderByLevelDesc(type);
    }
}
