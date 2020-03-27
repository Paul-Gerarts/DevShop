package be.syntra.devshop.DevshopBack.security.controllers.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PasswordChangeDto {

    private final String password;
}
