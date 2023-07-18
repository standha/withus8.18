package withus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Result<T> {
    private final Code code;
    private final T data;

    private Result(Code code, T data) {
        this.code = code;
        this.data = data;
    }

    public enum Code {
        OK, OK_NULL, ERROR, ERROR_DUPLICATE_ID, ERROR_DUPLICATE_CONTACT, ERROR_NO_EXIST_CAREGIVER, ERROR_DATABASE, ALREADY_CONTACT_EXIST, NO_INPUT_PATIENT, EROR_RELATION,
         ERROR_CAREGIVER_TEMP_CONTACT,ERROR_PATIENT_TEMP_CONTACT,ERROR_NOTHING_TO_DELETE, ERROR_PATIENT_REFERENCE, ERROR_SELF_REFERENCE, ERROR_CAREGIVER_REFERENCE
    }
}
