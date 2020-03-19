package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.testutilities.UserUtils;
import org.junit.jupiter.api.Test;

import static be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility.convertToProductDtoList;
import static be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility.convertToUser;
import static be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility.convertToUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperUtilityTest {

    @Test
    void convertToUserTest() {
        // given
        UserDto userDto = UserUtils.createUserDto();
        User user = UserUtils.createUser();

        // when
        User mappedUser = convertToUser(userDto);

        // then
        assertEquals(mappedUser.getClass(), User.class);
        assertEquals(mappedUser.getFirstName(), user.getFirstName());
        assertEquals(mappedUser.getLastName(), user.getLastName());
        assertEquals(mappedUser.getFullName(), user.getFullName());
        assertEquals(mappedUser.getPassword(), user.getPassword());
        assertEquals(mappedUser.getAddress().getStreet(), user.getAddress().getStreet());
        assertEquals(mappedUser.getAddress().getNumber(), user.getAddress().getNumber());
        assertEquals(mappedUser.getAddress().getBoxNumber(), user.getAddress().getBoxNumber());
        assertEquals(mappedUser.getAddress().getPostalCode(), user.getAddress().getPostalCode());
        assertEquals(mappedUser.getAddress().getCity(), user.getAddress().getCity());
        assertEquals(mappedUser.getAddress().getProvince(), user.getAddress().getProvince());
        assertEquals(mappedUser.getAddress().getCountry(), user.getAddress().getCountry());
        assertEquals(mappedUser.getActiveCart().getProducts(), user.getActiveCart().getProducts());
        assertEquals(mappedUser.getActiveCart().getCartCreationDateTime(), user.getActiveCart().getCartCreationDateTime());
        assertEquals(mappedUser.getArchivedCarts().get(0).getCartCreationDateTime(), user.getArchivedCarts().get(0).getCartCreationDateTime());
        assertEquals(mappedUser.getArchivedCarts().get(0).getProducts().get(0).getName(), user.getArchivedCarts().get(0).getProducts().get(0).getName());
        assertEquals(mappedUser.getArchivedCarts().get(0).getProducts().get(0).getPrice(), user.getArchivedCarts().get(0).getProducts().get(0).getPrice());

    }

    @Test
    void convertToUserDtoTest() {
        // given
        UserDto userDto = UserUtils.createUserDto();
        User user = UserUtils.createUser();

        // when
        UserDto mappedUserDto = convertToUserDto(user);

        // then
        assertEquals(mappedUserDto.getClass(), UserDto.class);
        assertEquals(mappedUserDto.getFirstName(), userDto.getFirstName());
        assertEquals(mappedUserDto.getLastName(), userDto.getLastName());
        assertEquals(mappedUserDto.getFullName(), userDto.getFullName());
        assertEquals(mappedUserDto.getPassword(), userDto.getPassword());
        assertEquals(mappedUserDto.getAddress().getStreet(), userDto.getAddress().getStreet());
        assertEquals(mappedUserDto.getAddress().getNumber(), userDto.getAddress().getNumber());
        assertEquals(mappedUserDto.getAddress().getBoxNumber(), userDto.getAddress().getBoxNumber());
        assertEquals(mappedUserDto.getAddress().getPostalCode(), userDto.getAddress().getPostalCode());
        assertEquals(mappedUserDto.getAddress().getCity(), userDto.getAddress().getCity());
        assertEquals(mappedUserDto.getAddress().getProvince(), userDto.getAddress().getProvince());
        assertEquals(mappedUserDto.getAddress().getCountry(), userDto.getAddress().getCountry());
        assertEquals(mappedUserDto.getActiveCart().getCartCreationDateTime(), userDto.getActiveCart().getCartCreationDateTime());
        assertEquals(mappedUserDto.getActiveCart().getProducts(), userDto.getActiveCart().getProducts());
        assertEquals(mappedUserDto.getArchivedCarts().get(0).getCartCreationDateTime(), user.getArchivedCarts().get(0).getCartCreationDateTime());
        assertEquals(mappedUserDto.getArchivedCarts().get(0).getProducts().get(0).getName(), convertToProductDtoList(user.getArchivedCarts().get(0).getProducts()).get(0).getName());
        assertEquals(mappedUserDto.getArchivedCarts().get(0).getProducts().get(0).getPrice(), convertToProductDtoList(user.getArchivedCarts().get(0).getProducts()).get(0).getPrice());

    }
}
