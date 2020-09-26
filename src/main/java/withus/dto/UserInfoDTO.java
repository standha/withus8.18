package withus.dto;

import org.springframework.beans.factory.annotation.Value;
import withus.entity.User;


public interface UserInfoDTO {

    String getPatientName();
    String getPatientId();
    String getPatientPassword();
    String getPatientBirthdate();
    User.Gender getPatientGender();
    String getPatientContact();
    String getCaregiverName();
    String getCaregiverId();
    String getCaregiverContact();
    String getCaregiverPassword();

}
