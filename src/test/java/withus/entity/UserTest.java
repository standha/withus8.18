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
}
