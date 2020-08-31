package withus.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_button_count")
public class Tbl_button_count {
    @Id
    @Column(name = "registration_count")
    private Integer registrationCount;

    @Column(name = "user_progress")
    private Integer userProgress;

    @Column(name = "goal_list", columnDefinition = "Integer default '0'")
    @ColumnDefault("0")
    private Integer goalList;

    @Column(name = "alarm")
    @ColumnDefault("0")
    private Integer alarm;

    @Column(name = "blood_pressure_pulse")
    @ColumnDefault("0")
    private Integer boolPressurePulse;

    @Column(name = "exercise")
    @ColumnDefault("0")
    private Integer exercise;

    @Column(name = "natrium_moisture")
    @ColumnDefault("0")
    private Integer natriumMoisture;

    @Column(name = "weight")
    @ColumnDefault("0")
    private Integer weight;

    @Column(name = "symptom")
    @ColumnDefault("0")
    private Integer symptom;

    @Column(name = "disease_info")
    @ColumnDefault("0")
    private Integer diseaseInfo;

    @Column(name = "withusrang")
    @ColumnDefault("0")
    private Integer withusrang;

    @Column(name = "helper")
    @ColumnDefault("0")
    private Integer helper;

    @Column(name = "withusrang_picture1")
    @ColumnDefault("0")
    private Integer withusrangPicture1;

    @Column(name = "withusrang_picture2")
    @ColumnDefault("0")
    private Integer withusrangPicture2;

    @Column(name = "withusrang_helper")
    @ColumnDefault("0")
    private Integer withusrangHelper;

}
