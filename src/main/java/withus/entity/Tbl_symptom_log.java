package withus.entity;

import lombok.*;
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
    private Boolean outofbreath;

    @Column(name = "tired")
    private Boolean tired;

    @Column(name = "ankle")
    private Boolean ankle;

    @Column(name = "cough")
    private Boolean cough;

    @Column(name = "todaySymptom")
    private Integer todaySymptom;
}