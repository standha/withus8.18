package withus.dto.wwithus;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import lombok.Setter;


public class HeaderInfoDTO {
    private String userId;

    private LocalDate birthdate;

    private Integer goal;

    private String name;

    public String getUserId() {
        return userId;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Integer getGoal() {
        return goal;
    }

    public String getName() {
        return name;
    }


    public HeaderInfoDTO(String name, String userId, LocalDate birthdate, Integer goal) {
        this.name = name;
        this.userId = userId;
        this.birthdate = birthdate;
        this.goal = goal;
    }
}
