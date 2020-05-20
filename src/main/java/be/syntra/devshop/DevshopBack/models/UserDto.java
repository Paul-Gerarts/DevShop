package be.syntra.devshop.DevshopBack.models;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private String fullName;
    private String password;
    private AddressDto address;
    private List<CartDto> archivedCarts;
    private CartDto cartDto;
}
