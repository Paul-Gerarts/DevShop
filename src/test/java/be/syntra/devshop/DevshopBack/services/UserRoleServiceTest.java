package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.exceptions.UserRoleNotFoundException;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import be.syntra.devshop.DevshopBack.security.repositories.UserRoleRepository;
import be.syntra.devshop.DevshopBack.security.services.UserRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_ADMIN;
import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserRoleServiceTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleService userRoleService;

    @BeforeEach
    public void init() {
        initMocks(this);
    }

    @Test
    void findUserRoleByNameTest() {
        // given
        Optional<UserRole> dummyRole = Optional.ofNullable(UserRole.builder().name(ROLE_USER.name()).build());
        when(userRoleRepository.findUserRoleByName(ROLE_USER.name())).thenReturn(dummyRole);

        // when
        Optional<UserRole> resultRole = Optional.ofNullable(userRoleService.findByRoleName(ROLE_USER.name()));

        // then
        assertThat(dummyRole).isEqualTo(resultRole);
        verify(userRoleRepository, times(1)).findUserRoleByName(ROLE_USER.name());
    }

    @Test
    void findAllTest() {
        // given
        UserRole dummyUserRole = Optional.ofNullable(UserRole.builder().name(ROLE_USER.name()).build()).orElse(null);
        UserRole dummyAdminRole = Optional.ofNullable(UserRole.builder().name(ROLE_ADMIN.name()).build()).orElse(null);
        Optional<List<UserRole>> dummyRoles = Optional.of(List.of(dummyUserRole, dummyAdminRole));
        when(userRoleService.findAll()).thenReturn(dummyRoles.get());

        // when
        Optional<List<UserRole>> resultRoles = Optional.of(userRoleService.findAll());

        // then
        assertThat(dummyRoles).isEqualTo(resultRoles);
        verify(userRoleRepository, times(1)).findAll();
    }

    @Test
    void exceptionIsThrownWhenUserRoleNotFoundTest() {
        // given
        String errorMessage = "userRole cannot be found!";
        when(userRoleRepository.findUserRoleByName("invalid name")).thenThrow(new UserRoleNotFoundException(errorMessage));

        // when - then
        assertThrows(UserRoleNotFoundException.class, () -> userRoleRepository.findUserRoleByName("invalid name"));
    }
}
