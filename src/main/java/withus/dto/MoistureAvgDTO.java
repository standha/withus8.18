package withus.dto;

public class MoistureAvgDTO {
    Integer week;
    Double intake;

    public Integer getWeek() {
        return week;
    }


    public Double getIntake() {
        return intake;
    }


    public MoistureAvgDTO(Integer week, Double intake) {
        this.week = week;
        this.intake = intake;
    }
}
