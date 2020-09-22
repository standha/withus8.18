package withus.entity;

import java.time.LocalDate;
import java.util.List;

import com.google.gson.internal.$Gson$Types;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.AnnotationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import withus.WithusApplication;
import withus.repository.MedicationRecordRepository;
import withus.repository.UserRepository;
import withus.repository.UserRepositorySupport;


@SpringBootTest
@SpringJUnitConfig(WithusApplication.class)
public class UserTest {
	@Autowired
	MedicationRecordRepository medicationRecordRepository;

	@Autowired
	private UserRepositorySupport userRepositorySupport;

	@Autowired
	UserRepository userRepository;

	@Test
	public void testUserRelation() {
		User caregiver = User.builder()
			.userId("son")
			.password("password")
			.name("son")
			.contact("5678")
			.type(User.Type.CAREGIVER)
			.build();

		User caretaker = User.builder()
			.userId("patient")
			.password("password")
			.name("patient")
			.contact("1234")
			.type(User.Type.PATIENT)
			.birthdate(LocalDate.of(2000, 2, 28))
			.gender(User.Gender.MALE)
			.caregiver(caregiver)
			.build();

		Assertions.assertThat(caretaker.getCaregiver().getContact()).isEqualTo("5678");
	}

	@Test
	public void testMedicationRecord() {
		LocalDate currentDate = LocalDate.now();
		String testId = "testID ";
		Tbl_medication_record recorTest = Tbl_medication_record.builder()
			//.id(new RecordKey(testId,currentDate))
			.pk(RecordKey.builder()
				.id(testId)
				.date(currentDate)
				.build()
			)
			.finished(true)
			.build();
		medicationRecordRepository.save(recorTest);
	}

	@Test
	public void queryDsl_기능확인(){

		User user = new User();
		user.setUserId("석");
		user.setType(User.Type.PATIENT);
		user.setPassword("pw");
		user.setName("name");
		userRepository.save(new User());
	/*	String name = "name";
		List<User> result = userRepositorySupport.findByName(name);

		System.out.println(result);*/
	}
}
