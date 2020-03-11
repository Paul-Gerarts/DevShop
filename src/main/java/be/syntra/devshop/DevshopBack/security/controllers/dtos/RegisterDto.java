package be.syntra.devshop.DevshopBack.security.controllers.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class RegisterDto {

    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
}
