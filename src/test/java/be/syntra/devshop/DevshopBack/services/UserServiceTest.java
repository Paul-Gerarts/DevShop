package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import be.syntra.devshop.DevshopBack.security.services.PasswordEncoderService;
import be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.SpyHelper.assertAllGettersCalled;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    @Mock
    private UserMapperUtility userMapperUtility;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void init() {
        initMocks(this);
    }

    @Test
    void getAllUsersTest() {
        // given
        List<User> dummyUsers = createUserList();
        when(userRepository.findAll()).thenReturn(dummyUsers);

        // when
        List<User> resultUserList = userService.findAll();

        // then
        assertEquals(resultUserList, dummyUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void saveUserTest() {
        // given
        UserDto dummyDto = createUserDto();

        // when
        UserDto resultUserDto = userService.save(dummyDto);

        // then
        assertEquals(dummyDto, resultUserDto);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void canRegisterUserTest() throws Exception {
        // given
        User dummyUser = createUser();
        when(passwordEncoderService.encode(any())).thenReturn("$2a$10$/fDjzeCFntx5VEv0cUjYG.heiUpSfloYQsn7Y2HID/ROGrtzAZmqC");
        when(userRepository.save(any())).thenReturn(dummyUser);

        // when
        User registeredUser = spy(userService.registerUser(
                dummyUser.getEmail(),
                dummyUser.getPassword(),
                dummyUser.getFirstName(),
                dummyUser.getLastName(),
                dummyUser.getUserRoles(),
                dummyUser.getAddress()));

        // then
        assertNotNull(registeredUser.getId());
        assertThat(registeredUser.getEmail()).isEqualTo(dummyUser.getEmail());
        assertThat(registeredUser.getPassword()).isEqualTo(dummyUser.getPassword());
        assertThat(registeredUser.getFirstName()).isEqualTo(dummyUser.getFirstName());
        assertThat(registeredUser.getLastName()).isEqualTo(dummyUser.getLastName());
        assertThat(registeredUser.getFullName()).isEqualTo(dummyUser.getFullName());
        assertThat(registeredUser.getUserRoles()).isEqualTo(dummyUser.getUserRoles());
        assertThat(registeredUser.getArchivedCarts()).isEqualTo(dummyUser.getArchivedCarts());
        assertThat(registeredUser.getActiveCart()).isEqualTo(dummyUser.getActiveCart());
        assertThat(registeredUser.getAddress()).isEqualTo(dummyUser.getAddress());
        assertAllGettersCalled(registeredUser);
        verify(userRepository, times(1)).save(any());
        verify(passwordEncoderService, times(1)).encode("password");
    }
}
