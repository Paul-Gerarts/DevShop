package be.syntra.devshop.DevshopBack.models;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.entities.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String fullName;
    private String password;
    private Address address;
    private List<Cart> archivedCarts;
    private Cart activeCart;
}
