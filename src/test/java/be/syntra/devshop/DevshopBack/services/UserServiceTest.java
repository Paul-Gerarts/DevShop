package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUserList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(UserServiceImpl.class)
public class UserServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void getAllUserTest() {
        //given
        List<User> users = createUserList();
        //when
        when(userService.findAll()).thenReturn(users);
        //then
        assertThat(userService.findAll().size()).isEqualTo(users.size());
    }


}
