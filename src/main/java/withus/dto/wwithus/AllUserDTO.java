package withus.dto.wwithus;

import lombok.Builder;
import lombok.Getter;
import withus.entity.User;
import withus.util.Utility;

import java.time.LocalDate;

@Builder
@Getter
public class AllUserDTO {
    private String name;
    private String userId;
    private String password;
    private LocalDate birthdate;
    private User.Gender gender;
    private String contact;
    private String guserName;
    private String guserId;
    private String guserPassword;
    private String guserContact;
    private String currentCode;
    private LocalDate userRecordDate;

    public static AllUserDTO fromString(String queryResult) {
        String[] array = queryResult.split(",");

        return AllUserDTO.builder()
                .name(array[0])
                .userId(array[1])
                .password(array[2])
                .birthdate(Utility.parseDate(array[3]))
                .gender(User.Gender.byName(array[4]))
                .contact(array[5])
                .guserName(array[6])
                .guserId(array[7])
                .guserPassword(array[8])
                .guserContact(array[9])
                .userRecordDate(Utility.parseDate(array[10]))
                .currentCode(array[11])
                .build();
    }
}
