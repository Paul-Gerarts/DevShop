package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.exceptions.UserAlreadyRegisteredException;
import be.syntra.devshop.DevshopBack.security.configuration.CorsConfiguration;
import be.syntra.devshop.DevshopBack.security.configuration.WebSecurityConfig;
import be.syntra.devshop.DevshopBack.security.controllers.AuthorizationController;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.LogInDto;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import be.syntra.devshop.DevshopBack.security.entities.JWTToken;
import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAccessDeniedHandler;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAuthenticationEntryPoint;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.security.repositories.UserRoleRepository;
import be.syntra.devshop.DevshopBack.security.services.UserRoleService;
import be.syntra.devshop.DevshopBack.services.UserServiceImpl;
import be.syntra.devshop.DevshopBack.testutilities.JsonUtils;
import be.syntra.devshop.DevshopBack.testutilities.UserUtils;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static be.syntra.devshop.DevshopBack.security.entities.UserRoles.ROLE_USER;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createRegisterDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @MockBean
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    public void setUp() {
        registerDto = UserUtils.createRegisterDto();
    }

    @Test
    @WithMockUser
    public void userCanLoginTest() throws Exception {
        // given
        RegisterDto dummyUser = createRegisterDto();
        userService.registerUser(dummyUser);
        when(userService.getNewJwtToken("paul.gerarts@juvo.be", "noop")).thenReturn(new JWTToken("eyJ1c2VySWQiOjIsImFsZyI6IkhTNTEyIn0.eyJzdWIiOiJwYXVsLmdlcmFydHNAanV2by5iZSIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiZXhwIjoxNTg1MjI5MjUxfQ.5ywx-83yFfg7rc65NAr194OeNV6MUUZQXh20t7kKSIg67MPt6uEShngwjFAVaV0IqZxlaUTSnaiss41EC3eKbg"));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(new LogInDto("paul.gerarts@juvo.be", "noop"))));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        JWTToken jwtToken = (JWTToken) jsonUtils.readValue(json, JWTToken.class);
        assertThat(jwtToken).isNotNull();
    }

    @Test
    public void userCanRegister() throws Exception {
        // given
        when(userRoleService.findByRoleName(ROLE_USER.name())).thenReturn(UserRole.builder().name(ROLE_USER.name()).build());

        // when
        HttpStatus resultStatus = registerUser(HttpStatus.CREATED, registerDto);

        // then
        assertThat(resultStatus).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void userCannotRegisterTwice() throws Exception {
        // given
        when(userService.registerUser(any())).thenThrow(new UserAlreadyRegisteredException("test"));
        when(userRoleService.findByRoleName(ROLE_USER.name())).thenReturn(UserRole.builder().name(ROLE_USER.name()).build());

        // when

        // then
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
