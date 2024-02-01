package nl.novi.serviceconnect.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, length = 255)
    private String password;

    @OneToMany(targetEntity = Authority.class, mappedBy = "username", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();
    @Column(unique = true,name = "email")
    @NotNull(message = "{NotNull.email}")
    @Email(message = "{Pattern.email}")
    @Size(min=10, max=50)
    private String email;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<Authority> getAuthorities() { return authorities; }
    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
    public void removeAuthority(Authority authority) { this.authorities.remove(authority); }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
