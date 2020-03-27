package be.syntra.devshop.DevshopBack.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private String fullName;
    private String password;
    private AddressDto address;
    private List<CartDto> archivedCarts;
    private CartDto activeCart;
}
