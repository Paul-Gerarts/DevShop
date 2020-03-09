package be.syntra.devshop.DevshopBack.security.controllers.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class RegisterCustomerDto {

    @NotBlank(message = "This field cannot be empty")
    @Email
    private final String email;
}
