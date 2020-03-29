package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;

import java.util.LinkedList;
import java.util.List;

public class UserUtils {

    public static User createUser() {
        return User.builder()
                .id(1L)
                .firstName("Someone")
                .lastName("First")
                .fullName("Someone First")
                .email("test@email.com")
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

    public static RegisterDto createRegisterDto() {
        return RegisterDto.builder()
                .firstName("Someone")
                .lastName("First")
                .password("password")
                .userName("test@email.com")
                .street("somewhere street")
                .number("1")
                .boxNumber("")
                .postalCode("1234")
                .city("Somewhere")
                .country("Belgium")
                .build();
    }

    public static User createUserWithId(Long id) {
        return User.builder()
                .id(id)
                .firstName("Someone")
                .lastName("First")
                .fullName("Someone First")
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
