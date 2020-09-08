package withus.dto.wwithus;

import lombok.Getter;
import lombok.Setter;
import withus.entity.User;

@Getter
public class WwithusEntryRequest {
	@Setter
	private User user;

	private String code;
}
