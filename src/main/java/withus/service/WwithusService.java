package withus.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.dto.wwithus.AnswerButton;
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
		WwithusEntry wwithusEntry;
		String code = wwithusEntryRequest.getNextCode();
		User user = wwithusEntryRequest.getUser();
		int week = user.getWeek();
		DayOfWeek dayOfWeek = wwithusEntryRequest.getDate().getDayOfWeek();
		if (code == null) {
			wwithusEntry = wwithusEntryRepository.findFirstByWeekAndDay(week, dayOfWeek).<RuntimeException>orElseThrow(() -> {
				throw new RuntimeException(String.format("Failed to select the first entry for %s, week %d.", dayOfWeek, week));
			});
		} else {
			wwithusEntry = wwithusEntryRepository.findById(code).<RuntimeException>orElseThrow(() -> {
				throw new RuntimeException(String.format("Failed to select entry by the code \"%s\".", code));
			});
		}

		WwithusEntryHistory wwithusEntryHistory = toWwithusEntryHistory(wwithusEntryRequest.getUser(), wwithusEntry);
		wwithusEntryHistoryRepository.save(wwithusEntryHistory);

		return wwithusEntry;
	}

	public List<WwithusEntry> getAnswerWwithusEntries(String currentCode) {
		return wwithusEntryRepository.findAllAnswersByCodeStartsWithButNotExactlyOrderByCode(currentCode);
	}

	@NonNull
	public List<WwithusEntryHistory> getWwithusEntryHistories(User user) {
		LocalDate today = LocalDate.now();

		return wwithusEntryHistoryRepository.findAllByUserAndDate(user, today);
	}

	public List<ChatBalloon> ToChatBalloons(@NonNull List<WwithusEntryHistory> wwithusEntryHistories) {
		List<ChatBalloon> chatBalloons = new ArrayList<>();
		for (WwithusEntryHistory wwithusEntryHistory : wwithusEntryHistories) {
			chatBalloons.add(toChatBalloon(wwithusEntryHistory));
		}

		return chatBalloons.stream()
			.sorted()
			.collect(Collectors.toList());
	}

	public ChatBalloon toChatBalloon(@NonNull WwithusEntry wwithusEntry) {
		boolean isAnswerExpected = wwithusEntry.isAnswerExpected();
		ChatBalloon.ChatBalloonBuilder chatBalloonBuilder = ChatBalloon.builder()
			.direction(ChatBalloon.Direction.LEFT)
			.isToTerminate(wwithusEntry.isAnswer() && wwithusEntry.getNextCode() == null)
			.isHelpRequest(wwithusEntry.isHelpRequest())
			.isAnswerExpected(isAnswerExpected)
			.content(wwithusEntry.getContent())
			.urlToImageFile(wwithusEntry.getUrlToImageFile())
			.urlToAudioFile(wwithusEntry.getUrlToAudioFile())
			.dateTime(LocalDateTime.now())
			.nextCode(wwithusEntry.getNextCode());

		if (isAnswerExpected) {
			List<AnswerButton> answerButtons = getAnswerButtons(wwithusEntry);
			chatBalloonBuilder.answerButtons(answerButtons);
		}

		return chatBalloonBuilder.build();
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
		boolean isAnswerExpected = wwithusEntry.isAnswerExpected();
		ChatBalloon.ChatBalloonBuilder chatBalloonBuilder = ChatBalloon.builder()
			.direction(wwithusEntry.isAnswer()? ChatBalloon.Direction.RIGHT: ChatBalloon.Direction.LEFT)
			.isToTerminate(wwithusEntry.isAnswer() && wwithusEntry.getNextCode() == null)
			.isHelpRequest(wwithusEntry.isHelpRequest())
			.isAnswerExpected(isAnswerExpected)
			.content(wwithusEntry.getContent())
			.urlToAudioFile(wwithusEntry.getUrlToAudioFile())
			.dateTime(wwithusEntryHistory.getDateTime())
			.nextCode(wwithusEntry.getNextCode());

		if (isAnswerExpected) {
			List<AnswerButton> answerButtons = getAnswerButtons(wwithusEntry);
			chatBalloonBuilder.answerButtons(answerButtons);
		}

		return chatBalloonBuilder.build();
	}

	private List<AnswerButton> getAnswerButtons(WwithusEntry wwithusEntry) {
		List<AnswerButton> answerButtons = new ArrayList<>();
		List<WwithusEntry> answerWwithusEntries = getAnswerWwithusEntries(wwithusEntry.getCode()).stream()
			.sorted()
			.collect(Collectors.toList());

		for (int i = 0; i < answerWwithusEntries.size(); i++) {
			WwithusEntry answerWwithusEntity = answerWwithusEntries.get(i);
			AnswerButton answerButton = AnswerButton.builder()
				.ordinal(i)
				.content(answerWwithusEntity.getContent())
				.urlToImageFile(answerWwithusEntity.getUrlToImageFile())
				.nextCode(answerWwithusEntity.getNextCode())
				.build();

			answerButtons.add(answerButton);
		}

		return answerButtons;
	}
}
