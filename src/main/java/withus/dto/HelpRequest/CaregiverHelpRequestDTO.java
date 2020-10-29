package withus.dto.HelpRequest;

import withus.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CaregiverHelpRequestDTO {
    private String name;
    private String id;
    private String contact;
    private String patientName;
    private String patientId;
    private String patientContact;
    private LocalDate requestDate;
    private LocalTime requestTime;

    public CaregiverHelpRequestDTO(String name, String id, String contact, String patientName, String patientId, String patientContact,
                                   LocalDate requestDate, LocalTime requestTime) {
        this.name = name;
        this.id = id;
        this.contact = contact;
        this.patientName = patientName;
        this.patientId = patientId;
        this.patientContact = patientContact;

        this.requestDate = requestDate;
        this.requestTime = requestTime;
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

    public String getPatientName() {
        return patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientContact() {
        return patientContact;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalTime getRequestTime() {
        return requestTime;
    }
}
