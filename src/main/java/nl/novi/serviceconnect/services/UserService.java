package nl.novi.serviceconnect.services;

import nl.novi.serviceconnect.exceptions.RecordNotFoundException;
import nl.novi.serviceconnect.helpper.Mapper;
import nl.novi.serviceconnect.models.User;
import org.springframework.stereotype.Service;
import nl.novi.serviceconnect.repository.UserRepository;
import nl.novi.serviceconnect.dtos.UserDto;
import nl.novi.serviceconnect.models.Authority;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository repo;


    public UserService(UserRepository repo) {
        this.repo = repo;
    }


    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = repo.findAll();
        for (User user : list) {
            collection.add(Mapper.fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = repo.findById(username);
        if (user.isPresent()){
            dto = Mapper.fromUser(user.get());
        }else {
            throw new RecordNotFoundException(username);
        }
        return dto;
    }

    public boolean userExists(String username) {
        return repo.existsById(username);
    }

    public String createUser(UserDto userDto) {
        User newUser = repo.save(Mapper.toUser(userDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        repo.deleteById(username);
    }

    public void updateUser(String username, UserDto newUser) {
        if (!repo.existsById(username)) throw new RecordNotFoundException();
        User user = repo.findById(username).get();
        user.setPassword(newUser.getPassword());
        repo.save(user);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!repo.existsById(username)) throw new RecordNotFoundException(username);
        User user = repo.findById(username).get();
        UserDto userDto = Mapper.fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!repo.existsById(username)) throw new RecordNotFoundException(username);
        User user = repo.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        repo.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!repo.existsById(username)) throw new RecordNotFoundException(username);
        User user = repo.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        repo.save(user);
    }
}
