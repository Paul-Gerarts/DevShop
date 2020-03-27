package be.syntra.devshop.DevshopBack.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserAlreadyRegisteredException.class,
            UserRoleNotFoundException.class})
    public ResponseEntity<?> giveHttpStatus(CustomException ce) {
        return new ResponseEntity<>(ce.getHttpStatus());
    }
}
