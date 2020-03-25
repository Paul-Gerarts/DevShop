package be.syntra.devshop.DevshopBack.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private HttpStatus status;
    private String title;
    private String message;

    public CustomException(String message, HttpStatus status, String title) {
        super(message);
        this.status = status;
        this.title = title;
        this.message = message;
    }
}
