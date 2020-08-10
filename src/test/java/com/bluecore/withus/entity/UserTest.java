package com.bluecore.withus.entity;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.bluecore.withus.WithusApplication;

@SpringBootTest
@SpringJUnitConfig(WithusApplication.class)
public class UserTest {
	@Test
	public void testUserRelation() {
		User caregiver = User.builder()
			.setId("son")
			.setContact("5678")
			.createUser();

		User caretaker = User.builder()
			.setId("patient")
			.setContact("1234")
			.setBirthdate(LocalDate.of(2000, 2, 28))
			.setCaregiver(caregiver)
			.createUser();

		Assertions.assertThat(caretaker.getCaregiver().getContact()).isEqualTo("5678");
	}
}
