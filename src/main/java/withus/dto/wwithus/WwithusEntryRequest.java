package withus.dto.wwithus;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;
import withus.entity.User;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class WwithusEntryRequest {
	private final User user;
	@Nullable
	private final String nextCode;
	@Builder.Default
	private final LocalDate date = LocalDate.now();
}
