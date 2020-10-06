package withus.dto.wwithus;

public class MoistureAvgDTO {
    Integer week;
    Double intake;

    public Integer getWeek() {
        return week;
    }
    public void setWeek(Integer week) {
        this.week = week;
    }

    public Double getIntake() {
        return intake;
    }

    public void setIntake(Double intake) {
        this.intake = intake;
    }

    public MoistureAvgDTO(Integer week, Double intake){
        this.week = week;
        this.intake = intake;
    }
}
