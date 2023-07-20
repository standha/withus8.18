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
@Table(name = "tbl_symptom_log")
public class Tbl_symptom_log {
    @EmbeddedId
    private RecordKey pk;

    @Column(name = "outofbreath")
    private Integer outofbreath;

    @Column(name = "tired")
    private Integer tired;

    @Column(name = "ankle")
    private Integer ankle;

    @Column(name = "cough")
    private Integer cough;

    @Column(columnDefinition = "VARCHAR(128)", length = 128, name = "text")
    @Getter
    private String text;

    @Column(name = "todaySymptom")
    private Integer todaysymptom;

    @Column(name = "week")
    private Integer week;
}