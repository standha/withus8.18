package withus.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.auth.AuthenticationFacade;
import withus.controller.BaseController;
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
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @Autowired
    public WwithusService(UserService userService, AuthenticationFacade authenticationFacade, WwithusEntryRepository wwithusEntryRepository, WwithusEntryHistoryRepository wwithusEntryHistoryRepository) {
        this.authenticationFacade = authenticationFacade;
        this.wwithusEntryRepository = wwithusEntryRepository;
        this.wwithusEntryHistoryRepository = wwithusEntryHistoryRepository;
        this.userService = userService;

    }

    public ChatBalloon getWwithusEntryAndSaveHistory(WwithusEntryRequest wwithusEntryRequest) {
        User user = wwithusEntryRequest.getUser();
        List<String> codesToSaveAsHistories = wwithusEntryRequest.getCodesToSaveAsHistories();
        String currentCode = wwithusEntryRequest.getCurrentCode();

        if (user.getType() == User.Type.CAREGIVER) {
            user = userService.getUserByCaregiverId(user.getUserId());
            if (currentCode == null) {
                throw new Utility.NoHisException();
            }
        }
        if (currentCode != null) {
            codesToSaveAsHistories.add(currentCode);
        }

        for (String codeToSaveAsHistory : codesToSaveAsHistories) {
            WwithusEntry currentEntry = wwithusEntryRepository.findById(codeToSaveAsHistory).<RuntimeException>orElseThrow(() -> {
                throw new RuntimeException(String.format("Failed to select entry by the code \"%s\".", currentCode));
            });

            user = wwithusEntryRequest.getUser();
            WwithusEntryHistory wwithusEntryHistory = toWwithusEntryHistory(wwithusEntryRequest.getUser(), currentEntry);
            WwithusEntryHistory existingHistory = wwithusEntryHistoryRepository.findById(wwithusEntryHistory.getKey()).orElse(null);
            if (existingHistory == null) {
                if (user.getType() == User.Type.PATIENT) {
                    wwithusEntryHistoryRepository.save(wwithusEntryHistory);
                } else {
                    user = userService.getUserByCaregiverId(user.getUserId());
                    if (currentCode == null) {
                        throw new Utility.NoHisException();
                    }
                }
            } else {
                log.debug("Chose not to overwrite a {}: {}", WwithusEntry.class.getSimpleName(), wwithusEntryHistory);
            }
        }

        WwithusEntry nextEntry;
        String nextCode = wwithusEntryRequest.getNextCode();
        System.out.printf("###### nextCode is %s ######\n", nextCode);

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
        List<WwithusEntry> answerNode = wwithusEntryRepository.findAllAnswersByCodeStartsWithButNotExactlyOrderByCode(currentCode + "_A");

        return answerNode;
    }

    @NonNull
    public List<ChatBalloon> getWwithusEntryHistories(User user, LocalDate date) {
        if (user.getType() == User.Type.CAREGIVER) {
            user = userService.getUserByCaregiverId(user.getUserId());
        }
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
                                        .isToRewind(true)
                                        .isToTerminate(false)
                                        .isHelpRequest(false)
                                        .content(TEMPORARY_POSITIVE)
                                        .nextCode(TEMPORARY_POSITIVE_HASH_CODE)
                                        .build(),
                                AnswerButton.builder()
                                        .ordinal(1)
                                        .code("DOES NOT REALLY MATTER")
                                        .isToRewind(false)
                                        .isToTerminate(true)
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
                .direction(wwithusEntry.isAnswer() ? ChatBalloon.Direction.RIGHT : ChatBalloon.Direction.LEFT)
                .isMostRecent(isLast)
                .isToTerminate(wwithusEntry.isLast())
                .isAnswerExpected(isAnswerExpected)
                .content(getUsernamePlusContent(wwithusEntry.getContent()))
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

    @NonNull
    public List<ChatBalloon> getNoPatientContent(User user) {
        List<ChatBalloon> NoPatientChatBalloons = new ArrayList<>();
        ChatBalloon noHisBalloon = ChatBalloon.builder()
                .code("AN ARBITRARY CODE")
                .direction(ChatBalloon.Direction.LEFT)
                .isMostRecent(false)
                .isToTerminate(true)
                .isAnswerExpected(false)
                .content("반갑습니다. 오늘은 저 위더스랑과 대화가 있는 날이에요. 오늘의 대화를 시작해 주세요.")
                .build();

        NoPatientChatBalloons.add(noHisBalloon);

        return NoPatientChatBalloons;
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
                    .content(answerWwithusEntity.getContent()) //버
                    .urlToImageFile(answerWwithusEntity.getUrlToImageFile())
                    .nextCode(answerWwithusEntity.getNextCode())
                    .build();

            answerButtons.add(answerButton);
        }

        return answerButtons;
    }

    public String getUsernamePlusContent(String content) {
        User user = userService.getUserById(authenticationFacade.getAuthentication().getName());

        if (user.getType() == User.Type.CAREGIVER) {
            user = userService.getUserByCaregiverId(user.getUserId());
        }

        String username = user.getName();
        if (content.startsWith("님 ")) {
            return username + content;
        } else {
            return content;
        }
    }

    @NonNull
    public ChatBalloon getNoHis(User user) {
        ChatBalloon noHisBalloon = ChatBalloon.builder()
                .code("NO HISTORY HERE")
                .content("반갑습니다. 오늘은 저 위더스랑과 대화가 있는 날이에요. 오늘의 대화를 시작해 주세요.")
                .build();

        return noHisBalloon;
    }
}
