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
@Table(name = "tbl_sympton_log")
public class Tbl_sympton_log {
    @Id
    @Column(name = "registration_Count")
    private Integer registrationCount;

    @Column(name = "sympton_Record")
    private Date symptonRecord;

    @Column(name = "out_Of_Breath")
    private Boolean outOfBreath;

    @Column(name = "tired_Exhausted")
    private Boolean tiredExhausted;

    @Column(name = "ankle_Swelling")
    private Boolean ankleSwelling;

    @Column(name = "cough_Unconfortable_Breathing")
    private Boolean coughUnconfortableBreathing;

}
