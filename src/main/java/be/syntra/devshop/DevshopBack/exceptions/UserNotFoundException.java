package be.syntra.devshop.DevshopBack.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
