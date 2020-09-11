package withus.service;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import withus.entity.User;
import withus.entity.WithusHelpRequest;
import withus.repository.WithusHelpRequestRepository;

@Service
@Slf4j
public class CenterService {
	private final WithusHelpRequestRepository withusHelpRequestRepository;

	@Autowired
	public CenterService(WithusHelpRequestRepository withusHelpRequestRepository) {
		this.withusHelpRequestRepository = withusHelpRequestRepository;
	}

	public WithusHelpRequest createHelpRequest(User user, LocalDateTime dateTime) {
		WithusHelpRequest withusHelpRequest = WithusHelpRequest.builder()
			.user(user)
			.dateTime(dateTime)
			.build();

		return withusHelpRequestRepository.save(withusHelpRequest);
	}
}
