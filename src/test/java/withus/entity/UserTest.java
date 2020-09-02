package withus.entity;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import withus.WithusApplication;

@SpringBootTest
@SpringJUnitConfig(WithusApplication.class)
public class UserTest {
	@Test
	public void testUserRelation() {
		User caregiver = User.builder()
			.setId("son")
			.setPassword("password")
			.setName("son")
			.setContact("5678")
			.setType(User.Type.CAREGIVER)
			.createUser();

		User caretaker = User.builder()
			.setId("patient")
			.setPassword("password")
			.setName("patient")
			.setContact("1234")
			.setType(User.Type.PATIENT)
			.setBirthdate(LocalDate.of(2000, 2, 28))
			.setGender(User.Gender.MALE)
			.setCaregiver(caregiver)
			.createUser();

		Assertions.assertThat(caretaker.getCaregiver().getContact()).isEqualTo("5678");
	}
}
