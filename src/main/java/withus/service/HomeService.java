package withus.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import withus.entity.User;
import withus.entity.WithusHelpRequest;
import withus.repository.WithusHelpRequestRepository;

@Service
public class HomeService {
    private final WithusHelpRequestRepository withusHelpRequestRepository;

    @Autowired
    public HomeService(WithusHelpRequestRepository withusHelpRequestRepository) {
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
