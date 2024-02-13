package nl.novi.serviceconnect.core.exceptions;


public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String username) {
        super("Cannot find user " + username);
    }
}
