package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import be.syntra.devshop.DevshopBack.security.entities.SecurityUser;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_USER;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createRegisterDto;
import static org.assertj.core.api.Assertions.assertThat;

public class UserFactoryTest {

    private UserFactory userFactory = new UserFactory();
    private SecurityUserFactory securityUserFactory = new SecurityUserFactory();
    private UserRoleFactory userRoleFactory = new UserRoleFactory();
    private AddressFactory addressFactory = new AddressFactory();

    @Test
    void canCreateUserOfSecurityTest() {
        // given
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "test@email.com";

        // when
        User resultUser = userFactory.ofSecurity(firstName, lastName, email);

        // then
        assertThat(resultUser.getClass()).isEqualTo(User.class);
        assertThat(resultUser.getFirstName()).isEqualTo(firstName);
        assertThat(resultUser.getLastName()).isEqualTo(lastName);
        assertThat(resultUser.getEmail()).isEqualTo(email);
    }

    @Test
    void canCreateUserFromRegisterDto() {
        // given
        RegisterDto dummyRegisterDto = createRegisterDto();
        Address dummyAddress = addressFactory.of(
                dummyRegisterDto.getStreet(),
                dummyRegisterDto.getNumber(),
                dummyRegisterDto.getBoxNumber(),
                dummyRegisterDto.getPostalCode(),
                dummyRegisterDto.getCity(),
                dummyRegisterDto.getCountry());

        // when
        User userResult = userFactory.ofRegisterDto(dummyRegisterDto);

        // then
        assertThat(userResult.getClass()).isEqualTo(User.class);
        assertThat(userResult.getFirstName()).isEqualTo(dummyRegisterDto.getFirstName());
        assertThat(userResult.getLastName()).isEqualTo(dummyRegisterDto.getLastName());
        assertThat(userResult.getFullName()).isEqualTo(dummyRegisterDto.getFirstName() + " " + dummyRegisterDto.getLastName());
        assertThat(userResult.getEmail()).isEqualTo(dummyRegisterDto.getUserName());
        assertThat(userResult.getAddress().toString()).isEqualTo(dummyAddress.toString());
    }

    @Test
    void canCreateSecurityUserTest() {
        // given
        String userName = "test@email.com";
        String password = "test";
        List<UserRole> userRoles = List.of(userRoleFactory.of(ROLE_USER.name()));

        // when
        SecurityUser securityUserResult = securityUserFactory.of(userName, password, userRoles);

        // then
        assertThat(securityUserResult.getClass()).isEqualTo(SecurityUser.class);
        assertThat(securityUserResult.getUsername()).isEqualTo(userName);
        assertThat(securityUserResult.getPassword()).isEqualTo(password);
        assertThat(securityUserResult.getUserRoles()).isEqualTo(userRoles);
        // assert that there are as much granted authorities as there are userRoles
        assertThat(securityUserResult.getUserRoles().size()).isEqualTo(securityUserResult.getAuthorities()
                .stream()
                // assert that the granted authorities derive from the correct userRole
                .map(authority -> userRoles.stream().map(userRole -> assertThat(authority).isEqualTo(userRole.getName())))
                .count());
    }
}
