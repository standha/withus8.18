package withus.dto;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
public class dateDTO {
    private LocalDate date;
    private String text;

    public dateDTO(LocalDate date, String text) {
        this.date = date;
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}
