package withus.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
		User user = wwithusEntryRequest.getUser();
		List<String> codesToSaveAsHistories = wwithusEntryRequest.getCodesToSaveAsHistories();
		String currentCode = wwithusEntryRequest.getCurrentCode();
		if (currentCode != null) { codesToSaveAsHistories.add(currentCode); }

		for (String codeToSaveAsHistory : codesToSaveAsHistories) {
			WwithusEntry currentEntry = wwithusEntryRepository.findById(codeToSaveAsHistory).<RuntimeException>orElseThrow(() -> {
				throw new RuntimeException(String.format("Failed to select entry by the code \"%s\".", currentCode));
			});

			WwithusEntryHistory wwithusEntryHistory = toWwithusEntryHistory(wwithusEntryRequest.getUser(), currentEntry);
			WwithusEntryHistory existingHistory = wwithusEntryHistoryRepository.findById(wwithusEntryHistory.getKey()).orElse(null);
			if (existingHistory == null) {
				wwithusEntryHistoryRepository.save(wwithusEntryHistory);
			} else {
				log.debug("Chose not to overwrite a {}: {}", WwithusEntry.class.getSimpleName(), wwithusEntryHistory);
			}
		}

		WwithusEntry nextEntry;
		String nextCode = wwithusEntryRequest.getNextCode();
		int week = user.getWeek();
		DayOfWeek dayOfWeek = wwithusEntryRequest.getDate().getDayOfWeek();
		if (nextCode == null) {
			nextEntry = wwithusEntryRepository.findFirstByWeekAndDay(week, dayOfWeek).<RuntimeException>orElseThrow(() -> {
				throw new RuntimeException(String.format("Failed to select the first entry for %s, week %d.", dayOfWeek, week));
			});
		} else {
			nextEntry = wwithusEntryRepository.findById(nextCode).<RuntimeException>orElseThrow(() -> {
				throw new RuntimeException(String.format("Failed to select entry by the code \"%s\".", nextCode));
			});
		}

		return toChatBalloon(nextEntry, true);
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
	public List<ChatBalloon> getReentrentChatBalloons(User user, LocalDate date) {
		List<ChatBalloon> reentrantChatBalloons = new ArrayList<>();

		String content = "다시 들어와주셨네요. 저 위더스랑과의 대화를 다시 하고 싶으세요?";

		final String TEMPORARY_POSITIVE = "예";
		final String TEMPORARY_NEGATIVE = "아니요";
		final String TEMPORARY_POSITIVE_HASH_CODE = String.valueOf(TEMPORARY_POSITIVE.hashCode());
		final String TEMPORARY_NEGATIVE_HASH_CODE = String.valueOf(TEMPORARY_NEGATIVE.hashCode());

		ChatBalloon interrogation = ChatBalloon.builder()
			.content(content)
			.answerButtons(
				Arrays.asList(
					AnswerButton.builder()
						.ordinal(0)
						.code("DOES NOT MATTER")
						.isToRewind(false)
						.isToTerminate(false)
						.isHelpRequest(false)
						.content(TEMPORARY_POSITIVE)
						.nextCode(TEMPORARY_POSITIVE_HASH_CODE)
						.build(),
					AnswerButton.builder()
						.ordinal(1)
						.code("DOES NOT REALLY MATTER")
						.isToRewind(false)
						.isToTerminate(false)
						.isHelpRequest(false)
						.content(TEMPORARY_NEGATIVE)
						.nextCode(TEMPORARY_NEGATIVE_HASH_CODE)
						.build()
				)
			).build();
		ChatBalloon positiveChatBalloon = ChatBalloon.builder()
			.code(TEMPORARY_POSITIVE_HASH_CODE)
			// TODO: 해당 날짜의 대화 처음부터 시작...
			.build();
		ChatBalloon negativeChatBalloon = ChatBalloon.builder()
			.code(TEMPORARY_NEGATIVE_HASH_CODE)
			// TODO
			.content(String.format("아쉽지만, %s에 다시 인사드릴게요.", Utility.getNextDayForWwithus(user.getWeek(), date.getDayOfWeek())))
			.build();

		reentrantChatBalloons.add(interrogation);
		reentrantChatBalloons.add(positiveChatBalloon);
		reentrantChatBalloons.add(negativeChatBalloon);

		return reentrantChatBalloons;
	}

	@NonNull
	public List<ChatBalloon> getCyaLaterChatBalloons(User user) {
		List<ChatBalloon> cyaLaterChatBalloons = new ArrayList<>();
		ChatBalloon cyaLaterBalloon = ChatBalloon.builder()
			.code("AN ARBITRARY CODE")
			.direction(ChatBalloon.Direction.LEFT)
			.isMostRecent(false)
			.isToTerminate(true)
			.isAnswerExpected(false)
			.content(Utility.getNonWwithusDayMessage(user.getWeek()))
			.build();

		cyaLaterChatBalloons.add(cyaLaterBalloon);

		return cyaLaterChatBalloons;
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

	private ChatBalloon toChatBalloon(@NonNull WwithusEntry wwithusEntry, boolean isLast) {
		return toChatBalloon(wwithusEntry, LocalDateTime.now(), isLast);
	}
	private ChatBalloon toChatBalloon(@NonNull WwithusEntryHistory wwithusEntryHistory, boolean isLast) {
		return toChatBalloon(wwithusEntryHistory.getEntry(), wwithusEntryHistory.getDateTime(), isLast);
	}
	private ChatBalloon toChatBalloon(@NonNull WwithusEntry wwithusEntry, @NonNull LocalDateTime dateTime, boolean isLast) {
		boolean isAnswerExpected = wwithusEntry.isAnswerExpected();
		ChatBalloon.ChatBalloonBuilder chatBalloonBuilder = ChatBalloon.builder()
			.code(wwithusEntry.getCode())
			.direction(wwithusEntry.isAnswer()? ChatBalloon.Direction.RIGHT: ChatBalloon.Direction.LEFT)
			.isMostRecent(isLast)
			.isToTerminate(wwithusEntry.isLast())
			.isAnswerExpected(isAnswerExpected)
			.content(wwithusEntry.getContent())
			.urlToImageFile(wwithusEntry.getUrlToImageFile())
			.urlToAudioFile(wwithusEntry.getUrlToAudioFile())
			.dateTime(dateTime)
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
				.code(answerWwithusEntity.getCode())
				.ordinal(i)
				.isHelpRequest(answerWwithusEntity.isHelpRequest())
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
