package withus.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_moisture")
public class Tbl_moisture {

    @Id
    @Column(name = "registration_Count")
    private Integer registrationCount;

    @Column(name = "moistrue_Record")
    private Date moistureRecord;

    @Column(name = "moisture_Intake")
    private Integer moistureIntake;


}
