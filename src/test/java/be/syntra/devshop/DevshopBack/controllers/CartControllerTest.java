package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.security.configuration.CorsConfiguration;
import be.syntra.devshop.DevshopBack.security.configuration.WebSecurityConfig;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAccessDeniedHandler;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAuthenticationEntryPoint;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.services.CartServiceImpl;
import be.syntra.devshop.DevshopBack.testutilities.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createCartDto;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({JsonUtils.class, WebSecurityConfig.class, CorsConfiguration.class, JWTTokenProvider.class, JWTAuthenticationEntryPoint.class, JWTAccessDeniedHandler.class})
@WebMvcTest(CartController.class)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @MockBean
    private CartServiceImpl cartService;


    @Test
    void createCartEndPointsTest() throws Exception {
        // given
        CartDto cartDtoDummy = createCartDto();
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/users/1/carts")
                                .contentType(APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(cartDtoDummy))

                );
        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.cartCreationDateTime").value(cartDtoDummy.getCartCreationDateTime().toString()))
                .andExpect(jsonPath("$.products[0].name").value(cartDtoDummy.getProducts().get(0).getName()))
                .andExpect(jsonPath("$.products[0].price").value(cartDtoDummy.getProducts().get(0).getPrice()))
                .andExpect(jsonPath("$.products[1].name").value(cartDtoDummy.getProducts().get(1).getName()))
                .andExpect(jsonPath("$.products[1].price").value(cartDtoDummy.getProducts().get(1).getPrice()));

    }
}
