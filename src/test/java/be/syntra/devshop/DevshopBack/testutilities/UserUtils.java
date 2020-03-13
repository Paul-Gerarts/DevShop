package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    public static Address createAddress() {
        return Address.builder()
                .street("somewhere street")
                .number("1")
                .boxNumber("")
                .postalCode("1234")
                .city("Somewhere")
                .province("Provence")
                .country("Belgium")
                .user(createUser())
                .build();
    }


    public static Cart createActiveCart() {
        List<Product> products = ProductUtils.createProductList();
        return Cart.builder()
                .user(createUser())
                .products(products)
                .cartCreationDateTime(LocalDateTime.now())
                .activeCart(true)
                .finalizedCart(false)
                .paidCart(false)
                .build();
    }


    public static User createUser() {
        List<Cart> carts = createArchivedCartList();
        Cart cart = createActiveCart();
        return User.builder()
                .firstName("Someone")
                .lastName("First")
                .fullName("Someone First")
                .password("password")
                .address(createAddress())
                .activeCart(cart)
                .archivedCarts(carts)
                .build();
    }

    public static UserDto createUserDto() {
        return UserDto.builder()
                .firstName("Someone")
                .lastName("First")
                .fullName("Someone First")
                .password("password")
                .address(createAddress())
                .activeCart(createActiveCart())
                .archivedCarts(createArchivedCartList())
                .build();
    }

    public static List<User> createUserList() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            users.add(createUser());
        }
        return users;
    }

    public static List<Cart> createArchivedCartList() {
        List<Cart> carts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            carts.add(createActiveCart());
        }
        return carts;
    }

}
