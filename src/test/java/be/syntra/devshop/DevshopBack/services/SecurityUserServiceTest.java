package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.factories.UserFactory;
import be.syntra.devshop.DevshopBack.security.entities.SecurityUser;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import be.syntra.devshop.DevshopBack.security.repositories.SecurityUserRepository;
import be.syntra.devshop.DevshopBack.security.services.SecurityUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_ADMIN;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SecurityUserServiceTest {

    @Mock
    private UserFactory userFactory;

    @Mock
    private SecurityUserRepository userRepository;

    @InjectMocks
    private SecurityUserService userService;

    @BeforeEach
    public void init() {
        initMocks(this);
    }

    @Test
    @WithMockUser
    void findByUserNameTest() {
        // given
        String testData = "test";
        SecurityUser dummyUser = SecurityUser.builder()
                .userName(testData)
                .password(testData)
                .userRoles(
                        List.of(UserRole.builder().name(ROLE_ADMIN.name()).build()))
                .build();
        when(userRepository.findByUserName(testData)).thenReturn(ofNullable(dummyUser));

        // when
        SecurityUser resultUser = userService.findByUserName(testData);

        // then
        assertEquals(resultUser, dummyUser);
        verify(userRepository, times(1)).findByUserName(testData);
    }
}
