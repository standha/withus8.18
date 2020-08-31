package withus.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_weight")
public class Tbl_weight {

    @Id
    @Column(name = "registration_Count")
    private Integer registrationCount;

    @Column(name = "weight_Record")
    private Date weightRecord;

    @Column(name = "weight")
    private Integer weight;

}
