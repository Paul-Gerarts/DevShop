package be.syntra.devshop.DevshopBack.security.controllers.dtos;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<UserRole> userRoles;
    private Address address;
}
