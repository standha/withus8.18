package withus.service;

import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import withus.auth.NoOpPasswordEncoder;
import withus.dto.MoistureAvgDTO;
import withus.dto.wwithus.HeaderInfoDTO;
import withus.entity.*;
import withus.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MedicationAlarmRepository medicationAlarmRepository;
    private final OutPatientVisitAlarmRepository outPatientVisitAlarmRepository;
    private final GoalRepository goalRepositroy;
    private final CountRepository countRepository;

    @Autowired
    public UserService(UserRepository userRepository, MedicationAlarmRepository medicationAlarmRepository, OutPatientVisitAlarmRepository outPatientVisitAlarmRepository,
                       GoalRepository goalRepositroy, CountRepository countRepository) {
        this.userRepository = userRepository;
        this.goalRepositroy = goalRepositroy;
        this.medicationAlarmRepository = medicationAlarmRepository;
        this.outPatientVisitAlarmRepository = outPatientVisitAlarmRepository;
        this.countRepository = countRepository;
    }

    @Autowired
    private UserRepositorySupport userRepositorySupport;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserId(username).orElseThrow(() -> {
                    String message = String.format("Username \"%s\" does not exist!", username);
                    return new UsernameNotFoundException(message);
                }
        );
    }

    @Nullable
    public User getUserByIdAndDate(String id) {
        User user =userRepository.findByUserId(id).orElse(null);
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
    public User getUserByContact(String contact) {
        return userRepository.findByContact(contact).orElse(null);
    }

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

        if (checkType == "PATIENT") {
            Tbl_medication_alarm tbl_medication_alarm = Tbl_medication_alarm.builder()
                    .id(saved.getUserId())
                    .build();

            medicationAlarmRepository.save(tbl_medication_alarm);

            Tbl_outpatient_visit_alarm tbl_outpatient_visit_alarm = Tbl_outpatient_visit_alarm.builder()
                    .id(saved.getUserId())
                    .visitAlarm(false)
                    .build();

            outPatientVisitAlarmRepository.save(tbl_outpatient_visit_alarm);

            Tbl_goal tbl_goal = Tbl_goal.builder()
                    .goalId(saved.getUserId())
                    .goal(0)
                    .build();

            goalRepositroy.save(tbl_goal);

            for (int i = 1; i <= 24; i++) {
                ProgressKey key = new ProgressKey(saved.getUserId(), i);
                Tbl_button_count tbl_button_count = Tbl_button_count.builder()
                        .key(key)
                        .alarm(0)
                        .bloodPressure(0)
                        .diseaseInfo(0)
                        .exercise(0)
                        .goal(0)
                        .helper(0)
                        .level(0)
                        .natriumMoisture(0)
                        .symptom(0)
                        .weight(0)
                        .withusRang(0)
                        .build();

                countRepository.save(tbl_button_count);
            }
        }

        return saved;
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
    public List<User> getPatientToken(User.Type type) {
        return userRepository.findByAppTokenIsNotNullAndType(type);
    }
}
