package withus.dto;

public class PillSumDTO {
    private Integer week;
    private Long recordSum;

    public Integer getWeek() {
        return week;
    }

    public Long getRecordSum() {
        return recordSum;
    }

    public PillSumDTO(Integer week, Long recordSum) {
        this.week = week;
        this.recordSum = recordSum;
    }
}
