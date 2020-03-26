package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.exceptions.UserAlreadyRegisteredException;
import be.syntra.devshop.DevshopBack.security.configuration.CorsConfiguration;
import be.syntra.devshop.DevshopBack.security.configuration.WebSecurityConfig;
import be.syntra.devshop.DevshopBack.security.controllers.AuthorizationController;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.LogInDto;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import be.syntra.devshop.DevshopBack.security.entities.JWTToken;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAccessDeniedHandler;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAuthenticationEntryPoint;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.security.services.UserRoleService;
import be.syntra.devshop.DevshopBack.services.UserServiceImpl;
import be.syntra.devshop.DevshopBack.testutilities.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorizationController.class)
@Import({JsonUtils.class, WebSecurityConfig.class, CorsConfiguration.class, JWTTokenProvider.class, JWTAuthenticationEntryPoint.class, JWTAccessDeniedHandler.class})
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @Value("${jwt.base64-secret}")
    private String jwtKey;

    @Mock
    private RegisterDto registerDto;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserRoleService userRoleService;

    @BeforeEach
    public void setUp() {
        registerDto = RegisterDto.builder()
                .firstName("Paul")
                .lastName("Gerarts")
                .email("paul.gerarts@juvo.be")
                .password("noop")
                .build();
    }

    @Test
    @WithMockUser
    public void userCanLoginTest() throws Exception {
        // given
        userService.registerUser("paul.gerarts@juvo.be", "noop", "Paul", "Gerarts", new ArrayList<>(), new Address());
        when(userService.getNewJwtToken("paul.gerarts@juvo.be", "noop")).thenReturn(new JWTToken("eyJ1c2VySWQiOjIsImFsZyI6IkhTNTEyIn0.eyJzdWIiOiJwYXVsLmdlcmFydHNAanV2by5iZSIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiZXhwIjoxNTg1MjI5MjUxfQ.5ywx-83yFfg7rc65NAr194OeNV6MUUZQXh20t7kKSIg67MPt6uEShngwjFAVaV0IqZxlaUTSnaiss41EC3eKbg"));
        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(new LogInDto("paul.gerarts@juvo.be", "noop"))))
                .andExpect(status().isOk()).andReturn();

        // then
        String json = mvcResult.getResponse().getContentAsString();
        JWTToken jwtToken = (JWTToken) jsonUtils.readValue(json, JWTToken.class);
        assertThat(jwtToken).isNotNull();
    }

    @Test
    public void userCanRegister() throws Exception {
        // given - when - then
        assertThat(registerUser(HttpStatus.CREATED, registerDto)).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void userCannotRegisterTwice() throws Exception {
        // given
        when(userService.registerUser(any(), any(), any(), any(), any(), any())).thenThrow(new UserAlreadyRegisteredException("test"));

        // when - then
        registerUser(HttpStatus.BAD_REQUEST, registerDto);
    }

    private HttpStatus registerUser(HttpStatus status, RegisterDto registerDto) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(registerDto)))
                .andExpect(status().is(status.value()));
        return status;
    }
}
