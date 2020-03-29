package be.syntra.devshop.DevshopBack.security.controllers.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LogInDto {

    private final String userName;
    private final String password;
}
