package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUserDto;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUserList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(UserServiceImpl.class)
public class UserServiceTest {

    @Mock
    private UserMapperUtility userMapperUtility;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
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
}
