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
                .address(AddressUtils.createAddressDto())
                .activeCart(CartUtils.createCartDto())
                .archivedCarts(CartUtils.createDummyCartDtoList())

                .build();
    }

    public static User createUserWithId(Long id) {
        return User.builder()
                .id(id)
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
        long id = 1L;
        List<User> users = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            users.add(createUserWithId(id++));
        }
        return users;
    }

}
