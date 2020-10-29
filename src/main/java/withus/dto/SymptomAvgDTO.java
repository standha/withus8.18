package withus.dto;

public class SymptomAvgDTO {
    private Integer week;
    private Integer outofbreath;
    private Integer tired;
    private Integer ankle;
    private Integer cough;

    public SymptomAvgDTO(Integer week, Integer outofbreath, Integer tired, Integer ankle, Integer cough) {
        this.week = week;
        this.outofbreath = outofbreath;
        this.tired = tired;
        this.ankle = ankle;
        this.cough = cough;
    }

    public Integer getWeek() {
        return week;
    }

    public Integer getOutofbreath() {
        return outofbreath;
    }

    public Integer getTired() {
        return tired;
    }

    public Integer getAnkle() {
        return ankle;
    }

    public Integer getCough() {
        return cough;
    }
}
