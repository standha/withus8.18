package withus.dto.wwithus;

import withus.entity.User;

public interface UserGenderCountDTO {
    User.Type getType();
    User.Gender getGender();
    Long getTotal();
}
