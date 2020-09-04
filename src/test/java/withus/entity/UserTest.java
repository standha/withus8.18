package withus.entity;

import java.time.LocalDate;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import withus.WithusApplication;
import withus.repository.MedicationRecordRepository;


@SpringBootTest
@SpringJUnitConfig(WithusApplication.class)
public class UserTest {
	@Autowired
	MedicationRecordRepository medicationRecordRepository;
	@Test
	public void testUserRelation() {

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
}
