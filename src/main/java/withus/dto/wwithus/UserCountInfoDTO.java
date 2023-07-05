package withus.dto.wwithus;

import java.time.LocalDateTime;
import java.util.List;

public class UserCountInfoDTO {
    private LocalDateTime UserCount;
    public LocalDateTime getUserCount() {
        return UserCount;
    }
    public UserCountInfoDTO(LocalDateTime UserCount){
        this.UserCount = UserCount;
    }
}
