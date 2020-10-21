package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import withus.auth.NoOpPasswordEncoder;
import withus.dto.HeaderInfoDTO;
import withus.dto.HelpRequest.CaregiverHelpRequestDTO;
import withus.dto.HelpRequest.PatientHelpRequestDTO;
import withus.dto.HelpRequestDTO;
import withus.dto.MoistureAvgDTO;
import withus.dto.PillSumDTO;
import withus.entity.*;
import withus.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final MedicationAlarmRepository medicationAlarmRepository;
    private final OutPatientVisitAlarmRepository outPatientVisitAlarmRepository;
    private final GoalRepository goalRepositroy;
    private final CountRepository countRepository;

    @Autowired
    public AdminService(UserRepository userRepository, MedicationAlarmRepository medicationAlarmRepository, OutPatientVisitAlarmRepository outPatientVisitAlarmRepository,
                        GoalRepository goalRepositroy, CountRepository countRepository) {
        this.userRepository = userRepository;
        this.goalRepositroy = goalRepositroy;
        this.medicationAlarmRepository = medicationAlarmRepository;
        this.outPatientVisitAlarmRepository = outPatientVisitAlarmRepository;
        this.countRepository = countRepository;
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
    public List<PillSumDTO> getPillSum(String userId) {
        return userRepositorySupport.findPillSum(userId);
    }

    @Nullable
    public List<Tbl_medication_record> getPillAsc(String userId) {
        return userRepositorySupport.findPillAsc(userId);
    }

    @Nullable
    public List<HelpRequestDTO> getHelpRequestAsc() {
        return userRepositorySupport.findHelpRequestAsc();
    }

    @Nullable
    public List<PatientHelpRequestDTO> getPatientRequest() {return userRepositorySupport.findPatientHelpRequest(); }

    @Nullable
    public List<CaregiverHelpRequestDTO> getCaregiverRequest() {return userRepositorySupport.findCaregiverHelpRequest(); }
}
