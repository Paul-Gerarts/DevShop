package be.syntra.devshop.DevshopBack.exceptions;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends CustomException {

    public CategoryNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
