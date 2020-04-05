package be.syntra.devshop.DevshopBack.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CustomException {

    public ProductNotFoundException(String errorMessage) {
        super(HttpStatus.NOT_FOUND, errorMessage);
    }
}
