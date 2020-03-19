package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.services.UserService;
import be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility;
import be.syntra.devshop.DevshopBack.testutilities.UserUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static be.syntra.devshop.DevshopBack.testutilities.GeneralUtils.asJsonString;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUserDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserController userController;

    @Mock
    private UserMapperUtility mapperUtility;

    @Mock
    private UserDto userDto;


    @Test
    void retrieveAllUsersEndPointTest() throws Exception {
        // Given
        UserDto newUser = createUserDto();
        // When
        when(userService.save(any(UserDto.class))).thenReturn(newUser);
        //then
        mockMvc.perform(post("/users")
                .content(asJsonString(newUser))
                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

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
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(userDtoDummy.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDtoDummy.getLastName()))
                .andExpect(jsonPath("$.fullName").value(userDtoDummy.getFullName()))
                .andExpect(jsonPath("$.password").value(userDtoDummy.getPassword()))
                .andExpect(jsonPath("$.address").value(userDtoDummy.getAddress()))
                .andExpect(jsonPath("$.activeCart").value(userDtoDummy.getActiveCart()))
                .andExpect(jsonPath("$.archivedCarts").value(userDtoDummy.getArchivedCarts()));


        verify(userService, times(1)).save(userDtoDummy);

    }


}
