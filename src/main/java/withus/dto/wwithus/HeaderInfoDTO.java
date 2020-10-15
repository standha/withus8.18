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

    private Integer level;

    public Integer getLevel() {
        return level;
    }

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

    public HeaderInfoDTO(String name, String userId, LocalDate birthdate, Integer goal, Integer level) {
        this.name = name;
        this.userId = userId;
        this.birthdate = birthdate;
        this.goal = goal;
        this.level = level;
    }
}
