package be.syntra.devshop.DevshopBack.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CustomException.class,
            UserAlreadyRegisteredException.class,
            UserRoleNotFoundException.class,
            UserNotFoundException.class,
            ProductNotFoundException.class})
    public ResponseEntity<ResponseEntity<CustomException>> giveHttpStatus(CustomException ce) {
        return new ResponseEntity<>(ce.getHttpStatus());
    }
}
