package be.syntra.devshop.DevshopBack.exceptions;

import org.springframework.http.HttpStatus;

public class UserRoleNotFoundException extends CustomException {

    public UserRoleNotFoundException(String errorMessage) {
        super(HttpStatus.NOT_FOUND, errorMessage);
    }
}
