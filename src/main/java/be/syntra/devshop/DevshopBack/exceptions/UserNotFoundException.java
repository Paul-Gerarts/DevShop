package be.syntra.devshop.DevshopBack.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message, "user not found");
    }
}