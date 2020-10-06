package withus.dto.wwithus;

public class MoistureAvgDTO {
    Integer week;
    Integer intake;

    public Integer getWeek() {
        return week;
    }
    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getIntake() {
        return intake;
    }

    public void setIntake(Integer intake) {
        this.intake = intake;
    }

    public MoistureAvgDTO(Integer week, Integer intake){
        this.week = week;
        this.intake = intake;
    }
}
