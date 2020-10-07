package withus.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_blood_pressure_pulse")
public class Tbl_blood_pressure_pulse {
    @EmbeddedId
    private RecordKey pk;

    @Column(name = "Contraction", columnDefinition = "int default 0")
    private Integer contraction;

    @Column(name = "relaxation ", columnDefinition = "int default 0")
    private Integer relaxation;

    @Column(name = "pressure", columnDefinition = "int default 0")
    private Integer pressure;

    @Column(name = "week")
    private Integer week;
}
