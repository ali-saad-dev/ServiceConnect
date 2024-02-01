package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.UserDto;
import nl.novi.serviceconnect.dtos.UserUpdateDto;
import nl.novi.serviceconnect.models.Authority;

import java.util.List;
import java.util.Set;

public interface IUserService {
    List<UserDto> getUsers();

    UserDto getUserByUserName(String username);

    boolean userExists(String username);

    String createUser(UserDto userDto);

    void updateUser(String username, UserUpdateDto newUser);

    void deleteUser(String username);

    Set<Authority> getAuthorities(String username);

    void addAuthority(String username, String authority);

    void removeAuthority(String username, String authority);
}
