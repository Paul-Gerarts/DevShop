package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.exceptions.UserAlreadyRegisteredException;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import be.syntra.devshop.DevshopBack.security.entities.JWTToken;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.security.services.PasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility.convertToUser;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final PasswordEncoderService passWordEncoderService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoderService passwordEncoderService,
                           AuthenticationManagerBuilder authenticationManagerBuilder,
                           JWTTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.passWordEncoderService = passwordEncoderService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto save(UserDto userDto) {
        userRepository.save(convertToUser(userDto));
        return userDto;
    }

    /*
     *@throws UserAlreadyRegisteredException
     *@See DataIntegrityViolationException ->
     * such Exception is thrown by three possible causes
     * org.hibernate.exception.ConstraintViolationException
     * org.hibernate.PropertyValueException
     * org.hibernate.exception.DataException
     * all of which have to do with persisting data to our database
     * in our case, it'll happen when an email-address has already been registered
     */
    public User registerUser(RegisterDto registerDto) throws UserAlreadyRegisteredException {
        try {
            User user = User.builder()
                    .email(registerDto.getEmail())
                    .password(passWordEncoderService.encode(registerDto.getPassword()))
                    .firstName(registerDto.getFirstName())
                    .lastName(registerDto.getLastName())
                    .userRoles(registerDto.getUserRoles())
                    .archivedCarts(new ArrayList<>())
                    .address(registerDto.getAddress())
                    .activeCart(new Cart())
                    .build();
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyRegisteredException("This email-address has already been registered! ");
        }
    }

    /*
     *@Return: new accessToken linked to logged in user, verified by authentication
     */
    public JWTToken getNewJwtToken(String email, String password) {
        String jwt = jwtTokenProvider.createToken(getAuthentication(email, password));
        return new JWTToken(jwt);
    }

    /*
     *@Return: authentication verified through email and password
     */
    private Authentication getAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

    /*
     *@Return a new authToken
     * which we need, because the User has changed his password
     * His previous authToken became thus invalid
     */
    public Authentication changeUserPassword(User currentUser, String newPassword) {
        currentUser.setPassword(passWordEncoderService.encode(newPassword));
        userRepository.save(currentUser);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                currentUser.getEmail(), newPassword, Arrays.stream(
                currentUser.getUserRoles().toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList()));
        SecurityContextHolder.getContext().setAuthentication(authenticationManagerBuilder.getObject().authenticate(authenticationToken));
        return authenticationToken;
    }
}
