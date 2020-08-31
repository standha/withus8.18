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
@Table(name = "tbl_natrium")
public class Tbl_natrium {
    @Id
    @Column(name = "registration_Count")
    private Integer registrationCount;

    @Column(name = "natrium_Record")
    private Date natriumRecord;

    @Column(name = "natrium_Morning")
    private Integer natriumMorning;

    @Column(name = "natrium_Launch")
    private Integer natriumLaunch;

    @Column(name = "natrium_Dinner")
    private Integer natriumDinner;
}
