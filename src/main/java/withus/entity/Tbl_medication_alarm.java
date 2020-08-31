package withus.entity;

import lombok.Builder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "TBL_MEDICATION_ALARM")
public class Tbl_medication_alarm {

    @Id
    @Column(name = "registration_Count")
    private Integer registrationCount;

    @Column(name = "medcation_Alarm_Onoff")
    private boolean medcationAlarmOnoff;

    @Column(name = "medication_Time_Morning")
    private LocalTime medicationTimeMorning;

    @Column(name = "medication_Time_Launch")
    private LocalTime medicationTimeLaunch;

    @Column(name = "medcation_Time_Dinner")
    private LocalTime medcationTimeDinner;

    @Builder
    public Tbl_medication_alarm(Integer registrationCount, boolean medcationAlarmOnoff, LocalTime medicationTimeMorning,
                                LocalTime medicationTimeLaunch, LocalTime medcationTimeDinner){
        this.registrationCount = registrationCount;
        this.medcationAlarmOnoff = medcationAlarmOnoff;
        this.medicationTimeMorning = medicationTimeMorning;
        this.medicationTimeLaunch = medicationTimeLaunch;
        this.medcationTimeDinner = medcationTimeDinner;
    }

}