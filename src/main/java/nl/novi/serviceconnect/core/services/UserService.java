package nl.novi.serviceconnect.core.services;

import nl.novi.serviceconnect.core.dtos.UserUpdateDto;
import nl.novi.serviceconnect.core.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.core.mappers.UserMapper;
import nl.novi.serviceconnect.infrastructure.models.User;
import org.springframework.stereotype.Service;
import nl.novi.serviceconnect.infrastructure.repository.UserRepository;
import nl.novi.serviceconnect.core.dtos.UserDto;
import nl.novi.serviceconnect.infrastructure.models.Authority;

import java.util.*;

@Service
public class UserService implements IUserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) { this.repo = repo; }

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = repo.findAll();
        for (User user : list) {
            collection.add(UserMapper.fromUser(user));
        }
        return collection;
    }

    @Override
    public UserDto getUserByUserName(String username) {

        Optional<User> user = repo.findById(username);

        if (user.isEmpty()) throw new RecordNotFoundException(username);

        return UserMapper.fromUser(user.get());
    }

    @Override
    public boolean userExists(String username) {
        return repo.existsById(username);
    }

    @Override
    public String createUser(UserDto userDto) {
        User newUser = repo.save(UserMapper.toUser(userDto));
        return newUser.getUsername();
    }

    @Override
    public void updateUser(String username, UserUpdateDto newUser) {
        if (!repo.existsById(username)) throw new RecordNotFoundException("user not found");
        User user = repo.findById(username).get();
        user.setEmail(newUser.getEmail());
        repo.save(user);
    }

    @Override
    public void deleteUser(String username) {
        repo.deleteById(username);
    }

    @Override
    public Set<Authority> getAuthorities(String username) {
        if (!repo.existsById(username)) throw new RecordNotFoundException(username);
        User user = repo.findById(username).get();
        UserDto userDto = UserMapper.fromUser(user);
        return userDto.getAuthorities();
    }

    @Override
    public void addAuthority(String username, String authority) {

        if (!repo.existsById(username)) throw new RecordNotFoundException(username);
        User user = repo.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        repo.save(user);
    }

    @Override
    public void removeAuthority(String username, String authority) {
        if (!repo.existsById(username)) throw new RecordNotFoundException(username);
        User user = repo.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        repo.save(user);
    }
}
