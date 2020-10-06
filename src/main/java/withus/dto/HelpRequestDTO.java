package withus.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HelpRequestDTO {
    private LocalDateTime requestDateTime;
    private String name;
    private String userId;
    private String contact;
    private String caregiverContact;
    private String helpCode;


    public HelpRequestDTO(LocalDateTime requestDateTime, String name, String userID, String contact, String caregiverContact, String helpCode) {
        this.requestDateTime = requestDateTime;
        this.name = name;
        this.userId = userID;
        this.contact = contact;
        this.caregiverContact = caregiverContact;
        this.helpCode = helpCode;
    }

    public LocalDateTime getRequestDateTime() {
        return requestDateTime;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getContact() {
        return contact;
    }

    public String getCaregiverContact() {
        return caregiverContact;
    }

    public String getHelpCode() {
        return helpCode;
    }
}
