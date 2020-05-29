package be.syntra.devshop.DevshopBack.exceptions;

import org.springframework.http.HttpStatus;

public class DeleteException extends CustomException {

    public DeleteException(String errorMessage) {
        super(HttpStatus.CONFLICT, errorMessage);
    }
}
