package be.syntra.devshop.DevshopBack.security;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import be.syntra.devshop.DevshopBack.security.controllers.AuthorizationController;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.LogInDto;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import be.syntra.devshop.DevshopBack.security.exceptions.UserAlreadyRegisteredException;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.security.models.JWTToken;
import be.syntra.devshop.DevshopBack.security.services.PasswordEncoderService;
import be.syntra.devshop.DevshopBack.security.services.UserService;
import be.syntra.devshop.DevshopBack.testutilities.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorizationController.class)
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @Value("${jwt.base64-secret}")
    private String jwtKey;

    @Mock
    private static RegisterDto registerDto;

    @Mock
    private static RegisterDto registerDto2;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @Mock
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    JWTTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        registerDto = RegisterDto.builder()
                .firstName("Paul")
                .lastName("Gerarts")
                .email("paul.gerarts@email.com")
                .password("noop")
                .build();
        registerDto2 = RegisterDto.builder()
                .firstName("Thomas")
                .lastName("Thomassen")
                .email("thomas.thomassen@email.com")
                .password("noop")
                .build();
    }

    @Test
    public void userCanLoginTest() throws Exception {
        userService.registerUser("paul.gerarts@email.com", "noop", "Paul", "Gerarts", new ArrayList<>());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(new LogInDto("paul.gerarts@email.com", "noop"))))
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        JWTToken jwtToken = new ObjectMapper().readValue(json, JWTToken.class);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(jwtToken.getToken());
        assertThat(claimsJws.getBody().get("sub")).isEqualTo("paul.gerarts@email.com");
        assertThat(claimsJws.getBody().get("auth").toString()).isEqualTo("[{authority=ROLE_ADMIN}, {authority=ROLE_USER}]");
    }

    @Test
    public void userCanRegister() {
        assertThrows(UserAlreadyRegisteredException.class, () -> {
            registerUser(status().is2xxSuccessful(), registerDto);
        });
    }

    @Test
    public void userCannotRegisterTwice() throws Exception {
        assertThat(registerUser(status().is2xxSuccessful(), registerDto2)).isEqualTo(HttpStatus.OK);
        assertThrows(UserAlreadyRegisteredException.class, () -> {
            registerUser(status().isBadRequest(), registerDto2);
        });
    }

    @Test
    public void canChangePassword() throws Exception {
        User user = User.builder()
                .firstName("firstName")
                .lastName("lastName")
                .password("password")
                .email("email@address.be")
                .userRoles(new ArrayList<>())
                .build();
        when(passwordEncoderService.encode(any())).thenReturn("$2a$10$/fDjzeCFntx5VEv0cUjYG.heiUpSfloYQsn7Y2HID/ROGrtzAZmqC");
        when(userRepository.save(any())).thenReturn(user);
        verify(passwordEncoderService, times(1)).encode("password");
        verify(userService, times(1)).changeUserPassword(user, "newPassword");
    }

    private HttpStatus registerUser(ResultMatcher status, RegisterDto registerDto) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(registerDto)))
                .andExpect(status);
        return HttpStatus.valueOf(String.valueOf(status));
    }
}
