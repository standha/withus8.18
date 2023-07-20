package withus.dto;

public class SymptomAvgDTO {
    private Integer week;
    private Integer outofbreath;
    private Integer tired;
    private Integer ankle;
    private Integer cough;
    private String text;

    public SymptomAvgDTO(Integer week, Integer outofbreath, Integer tired, Integer ankle, Integer cough, String text) {
        this.week = week;
        this.outofbreath = outofbreath;
        this.tired = tired;
        this.ankle = ankle;
        this.cough = cough;
        this.text = text;
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

    public String getText() { return text; }
}
