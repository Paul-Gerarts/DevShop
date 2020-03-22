package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.testutilities.UserUtils;
import org.junit.jupiter.api.Test;

import static be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility.convertToProductDtoList;
import static be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility.convertToProductList;
import static be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility.convertToUser;
import static be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility.convertToUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperUtilityTest {

    @Test
    void convertToUserTest() {
        // given
        UserDto userDto = UserUtils.createUserDto();

        // when
        User mappedUser = convertToUser(userDto);

        // then
        assertEquals(mappedUser.getClass(), User.class);
        assertEquals(mappedUser.getFirstName(), userDto.getFirstName());
        assertEquals(mappedUser.getLastName(), userDto.getLastName());
        assertEquals(mappedUser.getFullName(), userDto.getFullName());
        assertEquals(mappedUser.getPassword(), userDto.getPassword());
        assertEquals(mappedUser.getAddress().getStreet(), userDto.getAddress().getStreet());
        assertEquals(mappedUser.getAddress().getNumber(), userDto.getAddress().getNumber());
        assertEquals(mappedUser.getAddress().getBoxNumber(), userDto.getAddress().getBoxNumber());
        assertEquals(mappedUser.getAddress().getPostalCode(), userDto.getAddress().getPostalCode());
        assertEquals(mappedUser.getAddress().getCity(), userDto.getAddress().getCity());
        assertEquals(mappedUser.getAddress().getCountry(), userDto.getAddress().getCountry());
        assertEquals(mappedUser.getActiveCart().getProducts(), convertToProductList(userDto.getActiveCart().getProducts()));
        assertEquals(mappedUser.getActiveCart().getCartCreationDateTime(), userDto.getActiveCart().getCartCreationDateTime());
        assertEquals(mappedUser.getArchivedCarts().get(0).getCartCreationDateTime(), userDto.getArchivedCarts().get(0).getCartCreationDateTime());
        assertEquals(mappedUser.getArchivedCarts().get(0).getProducts().get(0).getName(), userDto.getArchivedCarts().get(0).getProducts().get(0).getName());
        assertEquals(mappedUser.getArchivedCarts().get(0).getProducts().get(0).getPrice(), userDto.getArchivedCarts().get(0).getProducts().get(0).getPrice());

    }

    @Test
    void convertToUserDtoTest() {
        // given
        User user = UserUtils.createUser();

        // when
        UserDto mappedUserDto = convertToUserDto(user);

        // then
        assertEquals(mappedUserDto.getClass(), UserDto.class);
        assertEquals(mappedUserDto.getFirstName(), user.getFirstName());
        assertEquals(mappedUserDto.getLastName(), user.getLastName());
        assertEquals(mappedUserDto.getFullName(), user.getFullName());
        assertEquals(mappedUserDto.getPassword(), user.getPassword());
        assertEquals(mappedUserDto.getAddress().getStreet(), user.getAddress().getStreet());
        assertEquals(mappedUserDto.getAddress().getNumber(), user.getAddress().getNumber());
        assertEquals(mappedUserDto.getAddress().getBoxNumber(), user.getAddress().getBoxNumber());
        assertEquals(mappedUserDto.getAddress().getPostalCode(), user.getAddress().getPostalCode());
        assertEquals(mappedUserDto.getAddress().getCity(), user.getAddress().getCity());
        assertEquals(mappedUserDto.getAddress().getCountry(), user.getAddress().getCountry());
        assertEquals(mappedUserDto.getActiveCart().getCartCreationDateTime(), user.getActiveCart().getCartCreationDateTime());
        assertEquals(mappedUserDto.getActiveCart().getProducts(), convertToProductDtoList(user.getActiveCart().getProducts()));
        assertEquals(mappedUserDto.getArchivedCarts().get(0).getCartCreationDateTime(), user.getArchivedCarts().get(0).getCartCreationDateTime());
        assertEquals(mappedUserDto.getArchivedCarts().get(0).getProducts().get(0).getName(), convertToProductDtoList(user.getArchivedCarts().get(0).getProducts()).get(0).getName());
        assertEquals(mappedUserDto.getArchivedCarts().get(0).getProducts().get(0).getPrice(), convertToProductDtoList(user.getArchivedCarts().get(0).getProducts()).get(0).getPrice());

    }
}
