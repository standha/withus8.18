package withus.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.dto.Result;
import withus.dto.wwithus.AnswerButton;
import withus.dto.wwithus.ChatBalloon;
import withus.dto.wwithus.WwithusEntryRequest;
import withus.entity.User;
import withus.entity.WwithusEntry;
import withus.entity.WwithusEntryHistory;
import withus.repository.WwithusEntryHistoryRepository;
import withus.repository.WwithusEntryRepository;
import withus.util.Utility;

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

	public ChatBalloon getWwithusEntryAndSaveHistory(WwithusEntryRequest wwithusEntryRequest) {
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

		return toChatBalloon(wwithusEntryHistory, true);
	}

	public List<WwithusEntry> getAnswerWwithusEntries(String currentCode) {
		return wwithusEntryRepository.findAllAnswersByCodeStartsWithButNotExactlyOrderByCode(currentCode);
	}

	@NonNull
	public List<ChatBalloon> getWwithusEntryHistories(User user, LocalDate date) {
		int week = user.getWeek();
		int day = Utility.getDayDigitForWwithus(week, date.getDayOfWeek());

		List<WwithusEntryHistory> wwithusEntryHistories = wwithusEntryHistoryRepository.findAllByUserAndWeekDay(user, week, day);

		List<ChatBalloon> chatBalloons = new ArrayList<>();
		Iterator<WwithusEntryHistory> iterator = wwithusEntryHistories.stream().sorted().iterator();
		while (iterator.hasNext()) {
			WwithusEntryHistory wwithusEntryHistory = iterator.next();

			boolean isMostRecent = !iterator.hasNext();
			chatBalloons.add(toChatBalloon(wwithusEntryHistory, isMostRecent));
		}

		return chatBalloons.stream()
			.sorted()
			.collect(Collectors.toList());
	}

	@NonNull
	public Result.Code deleteWwithusEntryHistories(User user, LocalDate date) {
		Result.Code code = Result.Code.OK;
		int week = user.getWeek();
		int day = Utility.getDayDigitForWwithus(week, date.getDayOfWeek());

		List<WwithusEntryHistory> wwithusEntryHistories = wwithusEntryHistoryRepository.findAllByUserAndWeekDay(user, week, day);
		if (wwithusEntryHistories.isEmpty()) {
			code = Result.Code.ERROR_NOTHING_TO_DELETE;
		} else {
			wwithusEntryHistoryRepository.deleteAll(wwithusEntryHistories);
		}

		return code;
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

	private ChatBalloon toChatBalloon(@NonNull WwithusEntryHistory wwithusEntryHistory, boolean isLast) {
		WwithusEntry wwithusEntry = wwithusEntryHistory.getEntry();
		boolean isAnswerExpected = wwithusEntry.isAnswerExpected();
		ChatBalloon.ChatBalloonBuilder chatBalloonBuilder = ChatBalloon.builder()
			.direction(wwithusEntry.isAnswer()? ChatBalloon.Direction.RIGHT: ChatBalloon.Direction.LEFT)
			.isMostRecent(isLast)
			.isHelpRequest(wwithusEntry.isHelpRequest())
			.isAnswerExpected(isAnswerExpected)
			.content(wwithusEntry.getContent())
			.urlToImageFile(wwithusEntry.getUrlToImageFile())
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
				.isToTerminate(answerWwithusEntity.isLast())
				.isToRewind(answerWwithusEntity.isToRewind())
				.content(answerWwithusEntity.getContent())
				.urlToImageFile(answerWwithusEntity.getUrlToImageFile())
				.nextCode(answerWwithusEntity.getNextCode())
				.build();

			answerButtons.add(answerButton);
		}

		return answerButtons;
	}
}
