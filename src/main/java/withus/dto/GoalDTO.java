package withus.dto;

import java.time.LocalDate;

public class GoalDTO {
    private String id;
    private Integer week;
    private String topGoal;
    private String middleGoal;
    private String bottomGoal;
    private LocalDate date;

    public Integer getWeek() {
        return week;
    }

    public String getTopGoal() {
        return topGoal;
    }

    public String getMiddleGoal() {
        return middleGoal;
    }

    public String getBottomGoal() {
        return bottomGoal;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public GoalDTO(String id, Integer week, String topGoal, String middleGoal, String bottomGoal, LocalDate date) {
        this.id = id;
        this.week = week;
        this.topGoal = topGoal;
        this.middleGoal = middleGoal;
        this.bottomGoal = bottomGoal;
        this.date = date;
    }
}
