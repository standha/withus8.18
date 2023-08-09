package withus.entity;

import lombok.*;
import org.springframework.lang.NonNull;
import withus.util.Utility;

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

    @EmbeddedId
    private RecordKey pk;

    @Column(name = "onoff_morning")
    private boolean alarmOnoffMorning = true;

    @Column(name = "onoff_lunch")
    private boolean alarmOnoffLunch = true;

    @Column(name = "onoff_dinner")
    private boolean alarmOnoffDinner = true;


    @Column(name="morning")
    private String morning;

    @Column(name="lunch")
    private String lunch;

    @Column(name="dinner")
    private String dinner;

    @Column(name = "medication_Time_Morning")
    private LocalTime medicationTimeMorning;

    @Column(name = "medication_Time_Lunch")
    private LocalTime medicationTimeLunch;

    @Column(name = "medication_Time_Dinner")
    private LocalTime medicationTimeDinner;

    @Column(name = "week")
    private Integer week;

    public String getDateString() {
        return Utility.format(pk.getDate());
    }


}