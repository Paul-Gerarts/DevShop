package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;

import java.util.LinkedList;
import java.util.List;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_ADMIN;
import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_USER;

public class UserUtils {

    public static User createUser() {
        return User.builder()
                .id(1L)
                .firstName("Someone")
                .lastName("First")
                .fullName("Someone First")
                .password("password")
                .email("test@email.com")
                .userRoles(List.of(UserRole.builder().name(ROLE_USER.name()).build(),
                        UserRole.builder().name(ROLE_ADMIN.name()).build()))
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
