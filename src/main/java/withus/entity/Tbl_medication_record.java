package withus.entity;

import lombok.*;
import withus.util.Utility;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_medication_record")
public class Tbl_medication_record {
    @EmbeddedId
    private RecordKey pk;

    @Column(name = "finished")
    private boolean finished;

    @Column(name = "week")
    private Integer week;

//    @Column(name="morning")
//    private String morning;
//
//    @Column(name="lunch")
//    private String lunch;
//
//    @Column(name="dinner")
//    private String dinner;

    public String getDateString() {
        return Utility.format(pk.getDate());
    }
}
