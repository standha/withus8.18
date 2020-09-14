package withus.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import withus.entity.RecordKey;

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

    @Column(name = "todaySymptom")
    private Integer todaysymptom;
}