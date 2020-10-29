package withus.dto;

public class WeightAvgDTO {
    private Integer week;
    private Double weight;

    public Integer getWeek() {
        return week;
    }

    public Double getWeight() {
        return weight;
    }

    public WeightAvgDTO(Integer week, Double weight) {
        this.week = week;
        this.weight = weight;
    }
}
