package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.factories.SecurityUserFactory;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.security.configuration.CorsConfiguration;
import be.syntra.devshop.DevshopBack.security.configuration.WebSecurityConfig;
import be.syntra.devshop.DevshopBack.security.entities.SecurityUser;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAccessDeniedHandler;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAuthenticationEntryPoint;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.security.services.SecurityUserService;
import be.syntra.devshop.DevshopBack.services.ShopOrderService;
import be.syntra.devshop.DevshopBack.services.utilities.ShopOrderMapper;
import be.syntra.devshop.DevshopBack.testutilities.JsonUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_ADMIN;
import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createCartDto;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({JsonUtils.class, WebSecurityConfig.class, CorsConfiguration.class, JWTTokenProvider.class, JWTAuthenticationEntryPoint.class, JWTAccessDeniedHandler.class})
@WebMvcTest(CartController.class)
public class ShopOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @Value("${frontend.userName}")
    private String userName;

    @Value("${frontend.password}")
    private String password;

    @Mock
    private SecurityUserFactory securityUserFactory;

    @MockBean
    private ShopOrderService shopOrderService;

    @MockBean
    private ShopOrderMapper shopOrderMapper;

    @MockBean
    private SecurityUserService securityUserService;

    @Test
    @WithMockUser
    void createCartEndPointsTest() throws Exception {
        // given
        SecurityUser frontendAuthentication = securityUserFactory.of(userName, password, List.of(UserRole.builder().name(ROLE_ADMIN.name()).build()));
        when(securityUserService.findByUserName(userName)).thenReturn(frontendAuthentication);
        CartDto cartDto = createCartDto();
        ShopOrder shopOrder = shopOrderMapper.convertToShopOrder(cartDto);
        when(shopOrderService.saveShopOrder(shopOrder, cartDto.getUser())).thenReturn(shopOrder);
        when(shopOrderMapper.convertToShopOrder(cartDto)).thenReturn(shopOrder);
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/cart")
                                .contentType(APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(cartDto))

                );
        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.cartProductDtoList[0].productDto.name").value(cartDto.getCartProductDtoList().get(0).getProductDto().getName()))
                .andExpect(jsonPath("$.cartProductDtoList[0].productDto.price").value(cartDto.getCartProductDtoList().get(0).getProductDto().getPrice()))
                .andExpect(jsonPath("$.cartProductDtoList[1].productDto.name").value(cartDto.getCartProductDtoList().get(1).getProductDto().getName()))
                .andExpect(jsonPath("$.cartProductDtoList[1].productDto.price").value(cartDto.getCartProductDtoList().get(1).getProductDto().getPrice()))
                .andExpect(jsonPath("$.finalizedCart").value(cartDto.isFinalizedCart()))
                .andExpect(jsonPath("$.paidCart").value(cartDto.isPaidCart()));

        verify(shopOrderService, times(1)).saveShopOrder(any(), anyString());
    }
}
