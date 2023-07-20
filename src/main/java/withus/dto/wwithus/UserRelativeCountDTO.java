package withus.dto.wwithus;

import withus.entity.User;

public interface UserRelativeCountDTO {
    User.Relative getRelative();

    Long getRelativeCount();
}
