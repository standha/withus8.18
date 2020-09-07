package withus.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import withus.util.Utility;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_mositrue_record")
public class Tbl_mositrue_record {
    @EmbeddedId
    private RecordKey pk;

    @Column(name = "moistureIntake", columnDefinition = "default 0")
    private Integer intake;
}
