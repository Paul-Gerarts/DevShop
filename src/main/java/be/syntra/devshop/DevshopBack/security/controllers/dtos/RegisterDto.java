package be.syntra.devshop.DevshopBack.security.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String userName;
    private String password;
    private String confirmedPassword;
    private String firstName;
    private String lastName;
    private String street;
    private String number;
    private String boxNumber;
    private String postalCode;
    private String city;
    private String country;
}
