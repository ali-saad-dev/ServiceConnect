package nl.novi.serviceconnect.exceptions;


public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String username) {
        super("Cannot find user " + username);
    }
}
