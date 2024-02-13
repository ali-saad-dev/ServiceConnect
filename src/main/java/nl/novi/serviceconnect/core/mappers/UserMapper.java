package nl.novi.serviceconnect.core.mappers;

import nl.novi.serviceconnect.core.dtos.UserDto;
import nl.novi.serviceconnect.infrastructure.models.User;

public class UserMapper {

    public static UserDto fromUser(User user) {

        UserDto dto = new UserDto();
        dto.username = user.getUsername();
        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();
        return dto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
