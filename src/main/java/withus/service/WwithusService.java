package withus.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.dto.wwithus.ChatBalloon;
import withus.dto.wwithus.WwithusEntryRequest;
import withus.entity.User;
import withus.entity.WwithusEntry;
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

	public WwithusEntry getWwithusEntryAndSaveHistory(WwithusEntryRequest wwithusEntryRequest) {
		String code = wwithusEntryRequest.getNextCode();
		WwithusEntry wwithusEntry = wwithusEntryRepository.findById(code).<RuntimeException>orElseThrow(() -> {
			throw new RuntimeException(String.format("Failed to select entry by the code \"%s\".", code));
		});

		WwithusEntryHistory wwithusEntryHistory = toWwithusEntryHistory(wwithusEntryRequest.getUser(), wwithusEntry);
		wwithusEntryHistoryRepository.save(wwithusEntryHistory);

		return wwithusEntry;
	}

	@NonNull
	public List<WwithusEntryHistory> getWwithusEntryHistories(User user) {
		LocalDate today = LocalDate.now();

		return wwithusEntryHistoryRepository.findAllByUserAndDate(user, today);
	}

	public ChatBalloon toChatBalloon(@NonNull WwithusEntry wwithusEntry) {
		return ChatBalloon.builder()
			.direction(ChatBalloon.Direction.LEFT)
			.isEmergencyCall(wwithusEntry.isEmergencyCall())
			.isAnswerExpected(wwithusEntry.isAnswerExpected())
			.content(wwithusEntry.getContent())
			.urlToAudioFile(wwithusEntry.getUrlToAudioFile())
			.dateTime(LocalDateTime.now())
			.nextCode(wwithusEntry.getNextCode())
			.build();
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

	private WwithusEntryHistory toWwithusEntryHistory(User user, WwithusEntry wwithusEntry) {
		return WwithusEntryHistory.builder()
			.key(
				WwithusEntryHistory.Key.builder()
					.user(user)
					.entry(wwithusEntry)
					.build()
			).build();
	}

	private ChatBalloon toChatBalloon(@NonNull WwithusEntryHistory wwithusEntryHistory) {
		WwithusEntry wwithusEntry = wwithusEntryHistory.getEntry();
		return ChatBalloon.builder()
			.direction(wwithusEntry.isAnswer()? ChatBalloon.Direction.RIGHT: ChatBalloon.Direction.LEFT)
			.isEmergencyCall(wwithusEntry.isEmergencyCall())
			.isAnswerExpected(wwithusEntry.isAnswerExpected())
			.content(wwithusEntry.getContent())
			.urlToAudioFile(wwithusEntry.getUrlToAudioFile())
			.dateTime(wwithusEntryHistory.getDateTime())
			.nextCode(wwithusEntry.getNextCode())
			.build();
	}
}
