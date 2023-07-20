package withus.dto.wwithus;

import lombok.Builder;
import lombok.Getter;
import withus.entity.User;
import withus.util.Utility;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class AllUserDTO {
    private String name;
    private String userId;
    private String password;
    private String height;
    private LocalDate birthdate;
    private User.Gender gender;
    private String contact;
    private String guserName;
    private User.Gender guserGender;
    private String guserId;
    private String guserPassword;
    private String guserContact;
    private String currentCode;
    private LocalDateTime userRecordDate;
    private String height;
    private User.Relative relative;

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
                .guserGender(User.Gender.byName(array[7]))
                .guserId(array[8])
                .guserPassword(array[9])
                .guserContact(array[10])
                .userRecordDate(Utility.parseDateTime(array[11]))
                .currentCode(array[12])
                .height(array[13])
                .relative(User.Relative.RbyName(array[14]))
                .build();
    }
}
