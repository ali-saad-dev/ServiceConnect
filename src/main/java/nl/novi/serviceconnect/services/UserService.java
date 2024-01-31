package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.dtos.UserUpdateDto;
import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.helpper.Mapper;
import nl.novi.serviceconnect.models.User;
import org.springframework.stereotype.Service;
import nl.novi.serviceconnect.repository.UserRepository;
import nl.novi.serviceconnect.dtos.UserDto;
import nl.novi.serviceconnect.models.Authority;

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
            collection.add(Mapper.fromUser(user));
        }
        return collection;
    }

    @Override
    public UserDto getUserByUserName(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = repo.findById(username);
        if (user.isPresent()){
            dto = Mapper.fromUser(user.get());
        }else {
            throw new RecordNotFoundException(username);
        }
        return dto;
    }

    @Override
    public boolean userExists(String username) {
        return repo.existsById(username);
    }

    @Override
    public String createUser(UserDto userDto) {
        User newUser = repo.save(Mapper.toUser(userDto));
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
        UserDto userDto = Mapper.fromUser(user);
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
