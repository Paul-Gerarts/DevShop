package be.syntra.devshop.DevshopBack.security.services;

import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.security.models.JwtToken;
import be.syntra.devshop.DevshopBack.security.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderService passWordEncoderService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JWTTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository,
                       PasswordEncoderService passWordEncoderService,
                       AuthenticationManagerBuilder authManageBuilder,
                       JWTTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passWordEncoderService = passWordEncoderService;
        this.authenticationManagerBuilder = authManageBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /*
     *@Return: new accessToken linked to logged in user, verified by authentication
     */
    public JwtToken getNewJwtToken(String email, String password) {
        String jwt = jwtTokenProvider.createToken(getAuthentication(email, password));
        return new JwtToken(jwt);
    }

    /*
     *@Return: authentication verified through email and password
     */
    private Authentication getAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

}
