package withus.dto.HelpRequest;

import withus.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PatientHelpRequestDTO {
    private String name;
    private String id;
    private String contact;
    private String caregiverContact;
    LocalDate requestDate;
    LocalTime requestTime;

    public PatientHelpRequestDTO(String name, String id, String contact, String caregiverContact, LocalDate requestDate, LocalTime requestTime) {
        this.name = name;
        this.id = id;
        this.contact = contact;
        this.caregiverContact = caregiverContact;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalTime getRequestTime() {
        return requestTime;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getContact() {
        return contact;
    }

    public String getCaregiverContact() {
        return caregiverContact;
    }
}