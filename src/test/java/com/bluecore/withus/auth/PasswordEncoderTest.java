package com.bluecore.withus.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.bluecore.withus.WithusApplication;
import com.bluecore.withus.entity.User;
import com.bluecore.withus.service.UserService;

@SpringBootTest
@SpringJUnitConfig(WithusApplication.class)
@Transactional
public class PasswordEncoderTest {
	@Autowired
	private UserService userService;

	private final User userWithPlaintextPassword = User.builder()
		.setId("id")
		.setPassword("password")
		.setContact("010")
		.setName("name")
		.setType(User.Type.PATIENT)
		.createUser();

	@Test
	public void testNoOpPasswordEncoder() {
		User saved = userService.upsertUser(userWithPlaintextPassword);

		Assertions.assertThat(saved.getPassword()).isEqualTo(userWithPlaintextPassword.getPassword());
		Assertions.assertThat(saved.getPassword()).isEqualTo(userWithPlaintextPassword.getPassword());
	}
}
