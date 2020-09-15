package withus.entity;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_medication_alarm")
public class Tbl_medication_alarm {
    @Id
    @Column(name = "medication_id", columnDefinition = "VARCHAR(128) NOT NULL", length = 128)
    @NonNull
    protected String id;

    @Column(name = "medication_Alarm_Onoff")
    private boolean medicationAlarmOnoff;


    @Column(name = "medication_Time_Morning")
    private LocalTime medicationTimeMorning;


    @Column(name = "medication_Time_Lunch")
    private LocalTime medicationTimeLunch;


    @Column(name = "medication_Time_Dinner")
    private LocalTime medicationTimeDinner;
}