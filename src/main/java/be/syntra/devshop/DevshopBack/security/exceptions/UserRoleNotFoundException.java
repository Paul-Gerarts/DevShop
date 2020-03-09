package be.syntra.devshop.DevshopBack.security.exceptions;

public class UserRoleNotFoundException extends Exception {
    public UserRoleNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
