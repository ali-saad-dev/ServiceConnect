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
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<Authority> getAuthorities() { return authorities; }
    public void setAuthorities(Set<Authority> authorities) { this.authorities = authorities; }
}
