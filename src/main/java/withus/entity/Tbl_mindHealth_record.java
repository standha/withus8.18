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
@Table(name = "tbl_mindhealth_record")
public class Tbl_mindHealth_record {
    @EmbeddedId
    private RecordKey pk;

    @Column(name = "mood")
    private Integer mood;

    @Column(name = "txt")
    private String text;

    @Column(name = "score")
    private Integer score;

    @Column(name="week")
    private Integer week;
}
