package withus.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import sun.plugin2.applet.context.NoopExecutionContext;
import withus.auth.NoOpPasswordEncoder;
import withus.entity.*;
import withus.repository.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Service
public class UserService implements UserDetailsService {
    private final PatientRepository patientRepository;
    private final GuardianRepository guardianRepository;
    private final GoalListRepository goalListRepository;
    private final UserProgressRepository userProgressRepository;
    private final ButtonCountRepository buttonCountRepository;
  //  private final


    @Autowired
    public UserService(PatientRepository patientRepository, GuardianRepository guardianRepository, GoalListRepository goalListRepository,
                       UserProgressRepository userProgressRepository, ButtonCountRepository buttonCountRepository){
        this.patientRepository = patientRepository;
        this.guardianRepository = guardianRepository;
        this.goalListRepository = goalListRepository;
        this.userProgressRepository = userProgressRepository;
        this.buttonCountRepository = buttonCountRepository;
    }


    @Override
    @Nonnull
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(guardianRepository.findByGuardianId(username).isPresent()){
            return guardianRepository.findByGuardianId(username).orElseThrow(()->{
                 String emthyMessage = String.format("User does not exist");
                 return new UsernameNotFoundException(emthyMessage);
            }
            );
        }else{
            return patientRepository.findByPatientId(username).orElseThrow(()->{
                        String emthyMessage = String.format("User does not exist");
                        return new UsernameNotFoundException(emthyMessage);
                    }
            );

        }
    }

    @Nullable
    public Tbl_patient getUserById(String patient) {
        return patientRepository.findByPatientId(patient).orElse(null);
    }

    @Nullable
    public Tbl_patient getUserByContact(String contact){return patientRepository.findByContact(contact).orElse(null);}

    @Nullable
    public Tbl_patient getUserByGcontact(String gcontact){return patientRepository.findByGcontact(gcontact).orElse(null);}

    @NonNull
    public Tbl_patient upsertUser(Tbl_patient tbl_patient) {return patientRepository.save(tbl_patient);}

    @Nullable
    public Tbl_guardian getUserByGid(String guardian) {return guardianRepository.findByGuardianId(guardian).orElse(null);}

    @Nullable
    public Tbl_guardian getUserByGtell(String gtell) {return guardianRepository.findByGtell(gtell).orElse(null);}

    @NonNull
    public Tbl_guardian upsertGuser(Tbl_guardian tbl_guardian) {return guardianRepository.save(tbl_guardian);}

    @NonNull
    public Tbl_patient upsertUserEncodingPassword(Tbl_patient tbl_patient){
        String plaintextPassword = tbl_patient.getPassword();
        NoOpPasswordEncoder noOpPasswordEncoder = NoOpPasswordEncoder.getInstance();
        String encodedPassword = noOpPasswordEncoder.encode(plaintextPassword);
        tbl_patient.setPassword(encodedPassword);
        Tbl_patient saved = patientRepository.save(tbl_patient);
        //Guardian table (PK)registrationCount, Gtell, LinkedPatientIdl set by patient table
        Tbl_guardian tbl_guardian = Tbl_guardian.builder()
                .setRegistrationCount(saved.getRegistrationCount())
                .setGtell(saved.getGcontact())
                .setLinkedPatientId(saved.getPatientId())
                .createGuardian();
        guardianRepository.save(tbl_guardian);
        //Goal List table (PK) registrationCount set by patient table
        Tbl_goal_list tbl_goal_list = Tbl_goal_list.builder()
                .registrationCount(saved.getRegistrationCount())
                .build();
        goalListRepository.save(tbl_goal_list);
        //User Progress table (PK) registrationCounts set by patient table
        Tbl_user_progress tbl_user_progress = Tbl_user_progress.builder()
                .registrationCount(saved.getRegistrationCount())
                .build();
        userProgressRepository.save(tbl_user_progress);
        //button Count table (PK) registrationCount set by patient table
        Tbl_button_count tbl_button_count = Tbl_button_count.builder()
                .registrationCount(saved.getRegistrationCount())
                .build();
        buttonCountRepository.save(tbl_button_count);
        return saved;
    }
    // UPDATE GUARDIAN
    @Nonnull
    public Tbl_guardian upsertGuardian(Tbl_guardian tbl_guardian) {
        Tbl_guardian found = guardianRepository.findByGtell(tbl_guardian.getGtell()).orElse(null);
        found.setGpassword(tbl_guardian.getPassword());
        found.setGname(tbl_guardian.getName());
        found.setGuardianId(tbl_guardian.getGuardianId());
        found.setG_tell(tbl_guardian.getGtell());

        return guardianRepository.save(found);
    }
}
