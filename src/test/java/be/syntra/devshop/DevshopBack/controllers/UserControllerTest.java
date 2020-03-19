package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.services.UserServiceImpl;
import be.syntra.devshop.DevshopBack.testutilities.UserUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.GeneralUtils.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;


    @Test
    void createUserEndPointsTest() throws Exception {
        // given
        UserDto userDtoDummy = UserUtils.createUserDto();
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/users")
                                .contentType(APPLICATION_JSON)
                                .content(asJsonString(userDtoDummy))
                );
        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON));


        verify(userService, times(1)).save(userDtoDummy);

    }

    @Test
    void testRetrieveAllUserEndpoint() throws Exception {
        // given
        List<User> userList = UserUtils.createUserList();
        when(userService.findAll()).thenReturn(userList);
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/users")
                );
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


        verify(userService, times(1)).findAll();
    }


}
