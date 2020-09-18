package withus.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_button_count")
public class Tbl_button_count {
    @EmbeddedId
    private ProgressKey key;

    @Column(name = "goal", columnDefinition = "Integer default 0")
    private Integer goal;

    @Column(name = "level")
    private Integer level;

    @Column(name = "alarm")
    private Integer alarm;

    @Column(name = "bloodPressure")
    private Integer bloodPressure;

    @Column(name = "exercise")
    private Integer exercise;

    @Column(name = "natriumMoisture")
    private Integer natriumMoisture;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "symptom")
    private Integer symptom;

    @Column(name = "diseaseInfo")
    private Integer diseaseInfo;

    @Column(name = "withusRang")
    private Integer withusRang;

    @Column(name = "helper")
    private Integer helper;
}
