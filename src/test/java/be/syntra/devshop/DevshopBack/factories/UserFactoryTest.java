package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_USER;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserFactoryTest {

    @Mock
    private UserFactory userFactory;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void canCreateUserOfSecurityTest() {
        // given
        User dummyUser = createUser();
        UserRole dummyUserRole = Optional.ofNullable(UserRole.builder().name(ROLE_USER.name()).build()).orElse(null);
        when(userFactory.ofSecurity("firstName", "password", List.of(dummyUserRole), "lastName", "test@email.com")).thenReturn(dummyUser);

        // when
        User resultUser = userFactory.ofSecurity("firstName", "password", List.of(dummyUserRole), "lastName", "test@email.com");

        // then
        assertThat(resultUser).isEqualTo(dummyUser);
    }
}
