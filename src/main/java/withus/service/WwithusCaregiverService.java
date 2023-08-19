package withus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.wwithus.AnswerButton;
import withus.dto.wwithus.ChatBalloon;
import withus.dto.wwithus.WwithusEntryRequest;
import withus.entity.User;
import withus.entity.WwithusEntryCaregiver;
import withus.entity.WwithusEntryHistoryCaregiver;
import withus.repository.WwithusEntryHistoryCaregiverRepository;
import withus.repository.WwithusEntryCaregiverRepository;
import withus.util.Utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WwithusCaregiverService {
    private final WwithusEntryCaregiverRepository wwithusEntryCaregiverRepository;
    private final WwithusEntryHistoryCaregiverRepository wwithusEntryHistoryCaregiverRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @Autowired
    public WwithusCaregiverService(UserService userService, AuthenticationFacade authenticationFacade, WwithusEntryCaregiverRepository wwithusEntryCaregiverRepository, WwithusEntryHistoryCaregiverRepository wwithusEntryHistoryCaregiverRepository) {
        this.authenticationFacade = authenticationFacade;
        this.wwithusEntryCaregiverRepository = wwithusEntryCaregiverRepository;
        this.wwithusEntryHistoryCaregiverRepository = wwithusEntryHistoryCaregiverRepository;
        this.userService = userService;

    }

    public ChatBalloon getWwithusEntryAndSaveHistory(WwithusEntryRequest wwithusEntryRequest) {
        User user = wwithusEntryRequest.getUser();
        List<String> codesToSaveAsHistories = wwithusEntryRequest.getCodesToSaveAsHistories();
        String currentCode = wwithusEntryRequest.getCurrentCode();

//        if (user.getType() == User.Type.CAREGIVER) {
//            user = userService.getUserByCaregiverId(user.getUserId());
//            if (currentCode == null) {
//                throw new Utility.NoHisException();
//            }
//        }

        if (currentCode != null) {
            codesToSaveAsHistories.add(currentCode);
        }

        for (String codeToSaveAsHistory : codesToSaveAsHistories) {
            WwithusEntryCaregiver currentEntry = wwithusEntryCaregiverRepository.findById(codeToSaveAsHistory).<RuntimeException>orElseThrow(() -> {
                throw new RuntimeException(String.format("Failed to select entry by the code \"%s\".", currentCode));
            });

            user = wwithusEntryRequest.getUser();
            WwithusEntryHistoryCaregiver wwithusEntryHistoryCaregiver = toWwithusEntryHistory(wwithusEntryRequest.getUser(), currentEntry);
            WwithusEntryHistoryCaregiver existingHistory = wwithusEntryHistoryCaregiverRepository.findById(wwithusEntryHistoryCaregiver.getKey()).orElse(null);
            if (existingHistory == null) {
                if (user.getType() == User.Type.CAREGIVER) {
                    wwithusEntryHistoryCaregiverRepository.save(wwithusEntryHistoryCaregiver);
                } else {
//                    user = userService.getUserByCaregiverId(user.getUserId());
                    if (currentCode == null) {
                        throw new Utility.NoHisException();
                    }
                }
            } else {
                log.debug("Chose not to overwrite a {}: {}", WwithusEntryCaregiver.class.getSimpleName(), wwithusEntryHistoryCaregiver);
            }
        }

        WwithusEntryCaregiver nextEntry;
        String nextCode = wwithusEntryRequest.getNextCode();
        System.out.printf("###### nextCode is %s ######\n", nextCode);

        int week = user.getWeek();
        DayOfWeek dayOfWeek = wwithusEntryRequest.getDate().getDayOfWeek();
        if (nextCode == null) {
            nextEntry = wwithusEntryCaregiverRepository.findFirstByWeekAndDay(week, dayOfWeek).<RuntimeException>orElseThrow(() -> {
                throw new RuntimeException(String.format("Failed to select the first entry for %s, week %d.", dayOfWeek, week));
            });
        } else {
            nextEntry = wwithusEntryCaregiverRepository.findById(nextCode).<RuntimeException>orElseThrow(() -> {
                throw new RuntimeException(String.format("Failed to select entry by the code \"%s\".", nextCode));
            });
        }

        return toChatBalloon(nextEntry, true);
    }

    public List<WwithusEntryCaregiver> getAnswerWwithusEntries(String currentCode) {
        List<WwithusEntryCaregiver> answerNode = wwithusEntryCaregiverRepository.findAllAnswersByCodeStartsWithButNotExactlyOrderByCode(currentCode + "_A");

        return answerNode;
    }

    @NonNull
    public List<ChatBalloon> getWwithusEntryHistories(User user, LocalDate date) {

//        if (user.getType() == User.Type.CAREGIVER) {
//            user = userService.getUserByCaregiverId(user.getUserId());
//        }

        int week = user.getWeek();
        int day = Utility.getDayDigitForWwithus(week, date.getDayOfWeek());

        List<WwithusEntryHistoryCaregiver> wwithusEntryHistories = wwithusEntryHistoryCaregiverRepository.findAllByUserAndWeekDay(user, week, day);

        List<ChatBalloon> chatBalloons = new ArrayList<>();
        Iterator<WwithusEntryHistoryCaregiver> iterator = wwithusEntryHistories.stream().sorted().iterator();
        while (iterator.hasNext()) {
            WwithusEntryHistoryCaregiver wwithusEntryHistoryCaregiver = iterator.next();

            boolean isMostRecent = !iterator.hasNext();
            chatBalloons.add(toChatBalloon(wwithusEntryHistoryCaregiver, isMostRecent));
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

        List<WwithusEntryHistoryCaregiver> wwithusEntryHistories = wwithusEntryHistoryCaregiverRepository.findAllByUserAndWeekDay(user, week, day);
        if (wwithusEntryHistories.isEmpty()) {
            code = Result.Code.ERROR_NOTHING_TO_DELETE;
        } else {
            wwithusEntryHistoryCaregiverRepository.deleteAll(wwithusEntryHistories);
        }

        return code;
    }

    private WwithusEntryHistoryCaregiver toWwithusEntryHistory(User user, WwithusEntryCaregiver wwithusEntryCaregiver) {
        return WwithusEntryHistoryCaregiver.builder()
                .key(
                        WwithusEntryHistoryCaregiver.Key.builder()
                                .user(user)
                                .entry(wwithusEntryCaregiver)
                                .build()
                ).build();
    }

    private ChatBalloon toChatBalloon(@NonNull WwithusEntryCaregiver wwithusEntryCaregiver, boolean isLast) {
        return toChatBalloon(wwithusEntryCaregiver, LocalDateTime.now(), isLast);
    }

    private ChatBalloon toChatBalloon(@NonNull WwithusEntryHistoryCaregiver wwithusEntryHistoryCaregiver, boolean isLast) {
        return toChatBalloon(wwithusEntryHistoryCaregiver.getEntry(), wwithusEntryHistoryCaregiver.getDateTime(), isLast);
    }

    private ChatBalloon toChatBalloon(@NonNull WwithusEntryCaregiver wwithusEntryCaregiver, @NonNull LocalDateTime dateTime, boolean isLast) {
        boolean isAnswerExpected = wwithusEntryCaregiver.isAnswerExpected();

        ChatBalloon.ChatBalloonBuilder chatBalloonBuilder = ChatBalloon.builder()
                .code(wwithusEntryCaregiver.getCode())
                .direction(wwithusEntryCaregiver.isAnswer() ? ChatBalloon.Direction.RIGHT : ChatBalloon.Direction.LEFT)
                .isMostRecent(isLast)
                .isToTerminate(wwithusEntryCaregiver.isLast())
                .isAnswerExpected(isAnswerExpected)
                .content(getUsernamePlusContent(wwithusEntryCaregiver.getContent()))
                .urlToImageFile(wwithusEntryCaregiver.getUrlToImageFile())
                .urlToAudioFile(wwithusEntryCaregiver.getUrlToAudioFile())
                .dateTime(dateTime)
                .nextCode(wwithusEntryCaregiver.getNextCode());

        if (isAnswerExpected) {
            List<AnswerButton> answerButtons = getAnswerButtons(wwithusEntryCaregiver);
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

    private List<AnswerButton> getAnswerButtons(WwithusEntryCaregiver wwithusEntryCaregiver) {

        List<AnswerButton> answerButtons = new ArrayList<>();
        List<WwithusEntryCaregiver> answerWwithusEntries = getAnswerWwithusEntries(wwithusEntryCaregiver.getCode()).stream()
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < answerWwithusEntries.size(); i++) {
            WwithusEntryCaregiver answerWwithusEntity = answerWwithusEntries.get(i);
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

//        if (user.getType() == User.Type.CAREGIVER) {
//            user = userService.getUserByCaregiverId(user.getUserId());
//        }

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
