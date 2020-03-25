package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.security.configuration.CorsConfiguration;
import be.syntra.devshop.DevshopBack.security.configuration.WebSecurityConfig;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAccessDeniedHandler;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAuthenticationEntryPoint;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.services.UserServiceImpl;
import be.syntra.devshop.DevshopBack.testutilities.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUserDto;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUserList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({JsonUtils.class, WebSecurityConfig.class, CorsConfiguration.class, JWTTokenProvider.class, JWTAuthenticationEntryPoint.class, JWTAccessDeniedHandler.class})
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @MockBean
    private UserServiceImpl userService;


    @Test
    void createUserEndPointsTest() throws Exception {
        // given
        UserDto userDtoDummy = createUserDto();
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/users")
                                .contentType(APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(userDtoDummy))
                );
        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON));


        verify(userService, times(1)).save(any());

    }

    @Test
    void testRetrieveAllUserEndpoint() throws Exception {
        // given
        List<User> userList = createUserList();
        when(userService.findAll()).thenReturn(userList);
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/users"));


        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].firstName").value(equalTo("Someone")))
                .andExpect(jsonPath("$[0].lastName").value(equalTo("First")))
                .andExpect(jsonPath("$[0].fullName").value(equalTo("Someone First")))
                .andExpect(jsonPath("$[0].password").value(equalTo("password")))
                .andExpect(jsonPath("$[0].address.street").value(equalTo("somewhere street")))
                .andExpect(jsonPath("$[0].address.number").value(equalTo("1")))
                .andExpect(jsonPath("$[0].address.boxNumber").value(equalTo("")))
                .andExpect(jsonPath("$[0].address.postalCode").value(equalTo("1234")))
                .andExpect(jsonPath("$[0].address.city").value(equalTo("Somewhere")))
                .andExpect(jsonPath("$[0].address.country").value(equalTo("Belgium")))
                .andExpect(jsonPath("$[0].archivedCarts[0].cartCreationDateTime").value(equalTo("28-03-2019 02:33:48")))
                .andExpect(jsonPath("$[0].archivedCarts[0].products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[0].archivedCarts[0].products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[0].archivedCarts[0].products[1].name").value(equalTo("product")))
                .andExpect(jsonPath("$[0].archivedCarts[0].products[1].price").value(equalTo(110)))
                .andExpect(jsonPath("$[0].archivedCarts[0].activeCart").value(equalTo(true)))
                .andExpect(jsonPath("$[0].archivedCarts[0].finalizedCart").value(equalTo(false)))
                .andExpect(jsonPath("$[0].archivedCarts[0].paidCart").value(equalTo(false)))
                .andExpect(jsonPath("$[0].archivedCarts[1].cartCreationDateTime").value(equalTo("18-03-2020 02:33:48")))
                .andExpect(jsonPath("$[0].archivedCarts[1].products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[0].archivedCarts[1].products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[0].archivedCarts[1].products[1].name").value(equalTo("product")))
                .andExpect(jsonPath("$[0].archivedCarts[1].products[1].price").value(equalTo(110)))
                .andExpect(jsonPath("$[0].archivedCarts[1].activeCart").value(equalTo(false)))
                .andExpect(jsonPath("$[0].archivedCarts[1].finalizedCart").value(equalTo(true)))
                .andExpect(jsonPath("$[0].archivedCarts[1].paidCart").value(equalTo(true)))
                .andExpect(jsonPath("$[0].activeCart.products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[0].activeCart.products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[0].activeCart.products[1].name").value(equalTo("product")))
                .andExpect(jsonPath("$[0].activeCart.products[1].price").value(equalTo(110)))
                .andExpect(jsonPath("$[0].activeCart.activeCart").value(equalTo(true)))
                .andExpect(jsonPath("$[0].activeCart.finalizedCart").value(equalTo(false)))
                .andExpect(jsonPath("$[0].activeCart.paidCart").value(equalTo(false)))
                .andExpect(jsonPath("$[1].firstName").value(equalTo("Someone")))
                .andExpect(jsonPath("$[1].lastName").value(equalTo("First")))
                .andExpect(jsonPath("$[1].fullName").value(equalTo("Someone First")))
                .andExpect(jsonPath("$[1].password").value(equalTo("password")))
                .andExpect(jsonPath("$[1].address.street").value(equalTo("somewhere street")))
                .andExpect(jsonPath("$[1].address.number").value(equalTo("1")))
                .andExpect(jsonPath("$[1].address.boxNumber").value(equalTo("")))
                .andExpect(jsonPath("$[1].address.postalCode").value(equalTo("1234")))
                .andExpect(jsonPath("$[1].address.city").value(equalTo("Somewhere")))
                .andExpect(jsonPath("$[1].address.country").value(equalTo("Belgium")))
                .andExpect(jsonPath("$[1].archivedCarts[0].cartCreationDateTime").value(equalTo("28-03-2019 02:33:48")))
                .andExpect(jsonPath("$[1].archivedCarts[0].products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[1].archivedCarts[0].products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[1].archivedCarts[0].products[1].name").value(equalTo("product")))
                .andExpect(jsonPath("$[1].archivedCarts[0].products[1].price").value(equalTo(110)))
                .andExpect(jsonPath("$[1].archivedCarts[0].activeCart").value(equalTo(true)))
                .andExpect(jsonPath("$[1].archivedCarts[0].finalizedCart").value(equalTo(false)))
                .andExpect(jsonPath("$[1].archivedCarts[0].paidCart").value(equalTo(false)))
                .andExpect(jsonPath("$[1].archivedCarts[1].cartCreationDateTime").value(equalTo("18-03-2020 02:33:48")))
                .andExpect(jsonPath("$[1].archivedCarts[1].products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[1].archivedCarts[1].products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[1].archivedCarts[1].products[1].name").value(equalTo("product")))
                .andExpect(jsonPath("$[1].archivedCarts[1].products[1].price").value(equalTo(110)))
                .andExpect(jsonPath("$[1].archivedCarts[1].activeCart").value(equalTo(false)))
                .andExpect(jsonPath("$[1].archivedCarts[1].finalizedCart").value(equalTo(true)))
                .andExpect(jsonPath("$[1].archivedCarts[1].paidCart").value(equalTo(true)))
                .andExpect(jsonPath("$[1].activeCart.products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[1].activeCart.products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[1].activeCart.products[1].name").value(equalTo("product")))
                .andExpect(jsonPath("$[1].activeCart.products[1].price").value(equalTo(110)))
                .andExpect(jsonPath("$[1].activeCart.activeCart").value(equalTo(true)))
                .andExpect(jsonPath("$[1].activeCart.finalizedCart").value(equalTo(false)))
                .andExpect(jsonPath("$[1].activeCart.paidCart").value(equalTo(false)))
                .andExpect(jsonPath("$[2].firstName").value(equalTo("Someone")))
                .andExpect(jsonPath("$[2].lastName").value(equalTo("First")))
                .andExpect(jsonPath("$[2].fullName").value(equalTo("Someone First")))
                .andExpect(jsonPath("$[2].password").value(equalTo("password")))
                .andExpect(jsonPath("$[2].address.street").value(equalTo("somewhere street")))
                .andExpect(jsonPath("$[2].address.number").value(equalTo("1")))
                .andExpect(jsonPath("$[2].address.boxNumber").value(equalTo("")))
                .andExpect(jsonPath("$[2].address.postalCode").value(equalTo("1234")))
                .andExpect(jsonPath("$[2].address.city").value(equalTo("Somewhere")))
                .andExpect(jsonPath("$[2].address.country").value(equalTo("Belgium")))
                .andExpect(jsonPath("$[2].archivedCarts[0].cartCreationDateTime").value(equalTo("28-03-2019 02:33:48")))
                .andExpect(jsonPath("$[2].archivedCarts[0].products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[2].archivedCarts[0].products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[2].archivedCarts[0].products[1].name").value(equalTo("product")))
                .andExpect(jsonPath("$[2].archivedCarts[0].products[1].price").value(equalTo(110)))
                .andExpect(jsonPath("$[2].archivedCarts[0].activeCart").value(equalTo(true)))
                .andExpect(jsonPath("$[2].archivedCarts[0].finalizedCart").value(equalTo(false)))
                .andExpect(jsonPath("$[2].archivedCarts[0].paidCart").value(equalTo(false)))
                .andExpect(jsonPath("$[2].archivedCarts[1].cartCreationDateTime").value(equalTo("18-03-2020 02:33:48")))
                .andExpect(jsonPath("$[2].archivedCarts[1].products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[2].archivedCarts[1].products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[2].archivedCarts[1].products[1].name").value(equalTo("product")))
                .andExpect(jsonPath("$[2].archivedCarts[1].products[1].price").value(equalTo(110)))
                .andExpect(jsonPath("$[2].archivedCarts[1].activeCart").value(equalTo(false)))
                .andExpect(jsonPath("$[2].archivedCarts[1].finalizedCart").value(equalTo(true)))
                .andExpect(jsonPath("$[2].archivedCarts[1].paidCart").value(equalTo(true)))
                .andExpect(jsonPath("$[2].activeCart.products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[2].activeCart.products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[2].activeCart.products[1].name").value(equalTo("product")))
                .andExpect(jsonPath("$[2].activeCart.products[1].price").value(equalTo(110)))
                .andExpect(jsonPath("$[2].activeCart.activeCart").value(equalTo(true)))
                .andExpect(jsonPath("$[2].activeCart.finalizedCart").value(equalTo(false)))
                .andExpect(jsonPath("$[2].activeCart.paidCart").value(equalTo(false)));
        verify(userService, times(1)).findAll();
    }


}
