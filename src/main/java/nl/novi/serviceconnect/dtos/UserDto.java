package nl.novi.serviceconnect.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.novi.serviceconnect.models.Authority;

import java.util.Set;


public class UserDto {

    public String username;

    public String password;
    public String email;
    @JsonSerialize
    public Set<Authority> authorities;
    public String getUsername() { return username; }
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public Set<Authority> getAuthorities() { return authorities; }
}
