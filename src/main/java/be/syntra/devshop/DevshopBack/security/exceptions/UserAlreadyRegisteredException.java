package be.syntra.devshop.DevshopBack.security.exceptions;

import javax.servlet.ServletException;

public class UserAlreadyRegisteredException extends ServletException {

    public UserAlreadyRegisteredException(String errorMessage) {
        super(errorMessage);
    }
}
