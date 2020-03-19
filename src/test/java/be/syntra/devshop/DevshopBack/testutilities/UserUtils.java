package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;

import java.util.LinkedList;
import java.util.List;

public class UserUtils {



    public static User createUser() {
        return User.builder()
                .firstName("Someone")
                .lastName("First")
                .fullName("Someone First")
                .password("password")
                .address(AddressUtils.createAddress())
                .activeCart(CartUtils.createActiveCart())
                .archivedCarts(CartUtils.createDummyCartList())
                .build();
    }

    public static UserDto createUserDto() {
        return UserDto.builder()
                .firstName("Someone")
                .lastName("First")
                .fullName("Someone First")
                .password("password")
                .archivedCarts(CartUtils.createDummyCartDtoList())
                .activeCart(CartUtils.createCartDto())
                .address(AddressUtils.createAddressDto())
                .build();
    }

    public static User createUserWithId() {
        return User.builder()
                .id(1L)
                .firstName("Someone")
                .lastName("First")
                .fullName("Someone First")
                .password("password")
                .address(AddressUtils.createAddress())
                .activeCart(CartUtils.createActiveCart())
                .archivedCarts(CartUtils.createDummyCartList())
                .build();
    }

    public static List<User> createUserList() {
        List<User> users = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            users.add(createUser());
        }
        return users;
    }

}
