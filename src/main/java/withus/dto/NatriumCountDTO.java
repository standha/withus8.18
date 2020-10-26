package withus.dto;

import lombok.Builder;

@Builder
public class NatriumCountDTO {
    private Integer week;
    private Integer noneCount;
    private Integer lowCount;
    private Integer mediumCount;
    private Integer highCount;

    public Integer getWeek() {
        return week;
    }

    public Integer getNoneCount() {
        return noneCount;
    }

    public Integer getLowCount() {
        return lowCount;
    }

    public Integer getMediumCount() {
        return mediumCount;
    }

    public Integer getHighCount() {
        return highCount;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public void setNoneCount(Integer noneCount) {
        this.noneCount = noneCount;
    }

    public void setLowCount(Integer lowCount) {
        this.lowCount = lowCount;
    }

    public void setMediumCount(Integer mediumCount) {
        this.mediumCount = mediumCount;
    }

    public void setHighCount(Integer highCount) {
        this.highCount = highCount;
    }
}
