package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_ADMIN;
import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserRoleFactoryTest {

    @Mock
    private UserRoleFactory userRoleFactory;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void canCreateUserRoleTest() {
        // given
        UserRole dummyUserRole = Optional.ofNullable(UserRole.builder().name(ROLE_USER.name()).build()).orElse(null);
        UserRole dummyAdminRole = Optional.ofNullable(UserRole.builder().name(ROLE_ADMIN.name()).build()).orElse(null);
        when(userRoleFactory.of(ROLE_USER.name())).thenReturn(dummyUserRole);
        when(userRoleFactory.of(ROLE_ADMIN.name())).thenReturn(dummyAdminRole);

        // when
        UserRole resultUserRole = userRoleFactory.of(ROLE_USER.name());
        UserRole resultAdminRole = userRoleFactory.of(ROLE_ADMIN.name());

        // then
        assertThat(resultUserRole).isEqualTo(dummyUserRole);
        assertThat(resultAdminRole).isEqualTo(dummyAdminRole);
    }
}
