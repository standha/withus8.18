package withus.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.dto.wwithus.ChatBalloon;
import withus.entity.User;
import withus.entity.WwithusEntryHistory;
import withus.repository.WwithusEntryHistoryRepository;
import withus.repository.WwithusEntryRepository;

@Service
@Slf4j
public class WwithusService {
	private final WwithusEntryRepository wwithusEntryRepository;
	private final WwithusEntryHistoryRepository wwithusEntryHistoryRepository;

	@Autowired
	public WwithusService(WwithusEntryRepository wwithusEntryRepository, WwithusEntryHistoryRepository wwithusEntryHistoryRepository) {
		this.wwithusEntryRepository = wwithusEntryRepository;
		this.wwithusEntryHistoryRepository = wwithusEntryHistoryRepository;
	}

	@NonNull
	public List<WwithusEntryHistory> getWwithusEntryHistories(User user) {
		LocalDate today = LocalDate.now();

		return wwithusEntryHistoryRepository.findAllByUserAndDate(user, today);
	}

	public List<ChatBalloon> toChatBalloons(@NonNull List<WwithusEntryHistory> wwithusEntryHistories) {
		List<ChatBalloon> chatBalloons = new ArrayList<>();
		for (WwithusEntryHistory wwithusEntryHistory : wwithusEntryHistories) {
			chatBalloons.add(toChatBalloon(wwithusEntryHistory));
		}

		return chatBalloons.stream()
			.sorted()
			.collect(Collectors.toList());
	}

	private ChatBalloon toChatBalloon(@NonNull WwithusEntryHistory wwithusEntryHistory) {
		return ChatBalloon.builder()
			// TODO
			.direction(ChatBalloon.Direction.LEFT)
			.content(wwithusEntryHistory.getEntry().getContent())
			.dateTime(wwithusEntryHistory.getDateTime())
			.build();
	}
}
