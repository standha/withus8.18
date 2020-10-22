package withus.dto;

public class ExerciseDTO {
    private Integer week;
    private Integer sumHour;
    private Integer sumMinute;
    private Long count;

    public ExerciseDTO(Integer week, Integer sumHour, Integer sumMinute, Long count){
        this.week = week;
        this.sumHour = sumHour;
        this.sumMinute = sumMinute;
        this.count = count;
    }

    public Integer getWeek() { return week; }
    public Integer getSumHour() { return sumHour; }
    public Integer getSumMinute() { return sumMinute; }
    public Long getCount() { return count; }
}
