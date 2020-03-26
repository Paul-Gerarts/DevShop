package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

public class UserFactoryTest {

    private UserFactory userFactory = new UserFactory();

    @Test
    void canCreateUserOfSecurityTest() {
        // given
        String firstName = "firstName";
        String password = "password";
        UserRole dummyUserRole = Optional.ofNullable(UserRole.builder().name(ROLE_USER.name()).build()).orElse(null);
        String lastName = "lastName";
        String email = "test@email.com";

        // when
        User resultUser = userFactory.ofSecurity(firstName, password, List.of(dummyUserRole), lastName, email);

        // then
        assertThat(resultUser.getClass()).isEqualTo(User.class);
        assertThat(resultUser.getFirstName()).isEqualTo(firstName);
        assertThat(resultUser.getPassword()).isEqualTo(password);
        assertThat(resultUser.getLastName()).isEqualTo(lastName);
        assertThat(resultUser.getEmail()).isEqualTo(email);
    }
}
