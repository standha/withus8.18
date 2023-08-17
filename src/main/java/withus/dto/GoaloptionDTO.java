package withus.dto;

import java.time.LocalDate;

public class GoaloptionDTO {
    private String id;
    private Integer week;
    private String goaloption;
    private String goalweek;
    private LocalDate date;

    public Integer getWeek() {
        return week;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getGoaloption() { return goaloption; }

    public String getGoalweek() { return goalweek; }

    public GoaloptionDTO(String id, Integer week, String goaloption, String goalweek, LocalDate date) {
        this.id = id;
        this.week = week;
        this.goaloption = goaloption;
        this.goalweek = goalweek;
        this.date = date;
    }
}
