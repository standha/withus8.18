package withus.dto.wwithus;

import java.time.LocalDate;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import withus.entity.User;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
public class WwithusEntryRequest {
    private User user;

    private String currentCode;

    @Nullable
    private String nextCode;

    private List<String> codesToSaveAsHistories;

    @Builder.Default
    private LocalDate date = LocalDate.now();
}
