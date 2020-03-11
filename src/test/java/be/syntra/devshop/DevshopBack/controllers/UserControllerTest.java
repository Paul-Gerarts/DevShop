package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.services.UserService;
import be.syntra.devshop.DevshopBack.services.utilities.MapperUtility;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.GeneralUtils.asJsonString;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUserDto;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUserList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserController userController;

    @Mock
    private MapperUtility mapperUtility;

    @Mock
    private UserDto userDto;

    @Test
    void addUserTest() throws Exception {
        // Given
        UserDto newUser = createUserDto();
        // When
        when(userService.save(any(UserDto.class))).thenReturn(newUser);
        //then
        mockMvc.perform(post("/users")
                .content(asJsonString(newUser))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void allUserEndPointsTest() throws Exception {
        // Given
        List<User> users = createUserList();
        UserDto singleUser = mapperUtility.convertToUserDto(users.get(0));
        //when
        when(userService.save(singleUser)).thenReturn(singleUser);
        when(userService.findAll()).thenReturn(users);
        //then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

}
