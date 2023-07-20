package withus.dto.wwithus;

import withus.entity.User;



public interface UserAgeCountDTO {
    String getAgeGroup();
    User.Type getType();
    Long getTotal();
}
