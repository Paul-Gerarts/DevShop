package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserFactoryTest {

    private UserFactory userFactory = new UserFactory();

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
}
