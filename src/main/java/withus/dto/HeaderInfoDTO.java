package withus.dto;

import java.time.LocalDate;

public class HeaderInfoDTO {
    private String userId;

    private LocalDate birthdate;

    private String goal;

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

    public String getGoal() {
        return goal;
    }

    public String getName() {
        return name;
    }

    public HeaderInfoDTO(String name, String userId, LocalDate birthdate, String goal, Integer level) {
        this.name = name;
        this.userId = userId;
        this.birthdate = birthdate;
        this.goal = goal;
        this.level = level;
    }
}
