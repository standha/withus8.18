package withus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import withus.auth.NoOpPasswordEncoder;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_outpatient_visit_alarm;
import withus.entity.User;
import withus.repository.MedicationAlarmRepository;
import withus.repository.OutPatientVisitAlarmRepository;
import withus.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@Service
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;
	private final MedicationAlarmRepository medicationAlarmRepository;
	private final OutPatientVisitAlarmRepository outPatientVisitAlarmRepository;
	@Autowired
	public UserService(UserRepository userRepository, MedicationAlarmRepository medicationAlarmRepository, OutPatientVisitAlarmRepository outPatientVisitAlarmRepository) {
		this.userRepository = userRepository;
		this.medicationAlarmRepository = medicationAlarmRepository;
		this.outPatientVisitAlarmRepository = outPatientVisitAlarmRepository;
	}

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
	public User getUserById(String id) {
		return userRepository.findByUserId(id).orElse(null);
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
		User caregiver = new User();
		String checkType = user.getType().name();
		System.out.println("Making patient table to Id(String)"+checkType);
		if(checkType == "PATIENT") {
			Tbl_medication_alarm tbl_medication_alarm = Tbl_medication_alarm.builder()
					.id(saved.getUserId())
					.build();
			medicationAlarmRepository.save(tbl_medication_alarm);

			Tbl_outpatient_visit_alarm tbl_outpatient_visit_alarm = Tbl_outpatient_visit_alarm.builder()
					.id(saved.getUserId())
					.build();
			outPatientVisitAlarmRepository.save(tbl_outpatient_visit_alarm);
		}
		return saved;

	}

	public List<User> getAllPatient()
	{
		return userRepository.findAll();
	}
	
	@Nullable
	public List<User> getAllToken(){
		return userRepository.findByAppTokenIsNotNull();
	}
}
