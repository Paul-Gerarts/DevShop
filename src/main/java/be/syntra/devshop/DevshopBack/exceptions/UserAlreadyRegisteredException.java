package be.syntra.devshop.DevshopBack.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyRegisteredException extends CustomException {

    public UserAlreadyRegisteredException(String errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
