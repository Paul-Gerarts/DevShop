package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.testutilities.UserUtils;
import org.junit.jupiter.api.Test;

import static be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility.convertToUser;
import static be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility.convertToUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperUtilityTest {

    private UserMapperUtility userMapperUtility = new UserMapperUtility();

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
        assertEquals(mappedUser.getAddress().toString(), user.getAddress().toString());
        assertEquals(mappedUser.getActiveCart().toString(), user.getActiveCart().toString());
        assertEquals(mappedUser.getArchivedCarts().toString(), user.getArchivedCarts().toString());

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
        assertEquals(mappedUserDto.getAddress().toString(), userDto.getAddress().toString());
        assertEquals(mappedUserDto.getActiveCart().toString(), userDto.getActiveCart().toString());
        assertEquals(mappedUserDto.getArchivedCarts().toString(), userDto.getArchivedCarts().toString());


    }
}
