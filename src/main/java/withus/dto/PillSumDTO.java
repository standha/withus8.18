package withus.dto;

public class PillSumDTO {
    private Integer week;


    private Integer recordSum;

    public Integer getWeek() {
        return week;
    }

    public Integer getRecordSum() {
        return recordSum;
    }

    public PillSumDTO(Integer week, Integer recordSum){
        this.week = week;
        this.recordSum = recordSum;
    }
}
