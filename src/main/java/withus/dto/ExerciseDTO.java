package withus.dto;

public class ExerciseDTO {
    private Integer week;
    private Long walkingCount;
    private Long cyclingCount;
    private Long swimmingCount;
    private Long strengthCount;


    public ExerciseDTO(Integer week, Long walkingCount, Long cyclingCount, Long swimmingCount, Long strengthCount) {
        this.week = week;
        this.walkingCount = walkingCount;
        this.cyclingCount = cyclingCount;
        this.swimmingCount = swimmingCount;
        this.strengthCount = strengthCount;
    }

    public Integer getWeek() {
        return week;
    }

    public Long getWalkingCount() {
        return walkingCount;
    }

    public Long getCyclingCount() {
        return cyclingCount;
    }

    public Long getSwimmingCount() {
        return swimmingCount;
    }

    public Long getStrengthCount() {
        return strengthCount;
    }
}
