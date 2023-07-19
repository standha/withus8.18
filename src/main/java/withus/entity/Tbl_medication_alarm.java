package withus.entity;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
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
    public String id;

    @Column(name = "medication_alarm_onoff_morning")
    private boolean alarmOnoffMorning;

    @Column(name = "medication_alarm_onoff_lunch")
    private boolean alarmOnoffLunch;

    @Column(name = "medication_alarm_onoff_dinner")
    private boolean alarmOnoffDinner;

    @Column(name = "medication_Time_Morning")
    private LocalTime medicationTimeMorning;

    @Column(name = "medication_Time_Lunch")
    private LocalTime medicationTimeLunch;

    @Column(name = "medication_Time_Dinner")
    private LocalTime medicationTimeDinner;



}