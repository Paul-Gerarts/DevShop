package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.exceptions.UserAlreadyRegisteredException;
import be.syntra.devshop.DevshopBack.exceptions.UserNotFoundException;
import be.syntra.devshop.DevshopBack.factories.UserFactory;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import be.syntra.devshop.DevshopBack.security.entities.JWTToken;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JWTTokenProvider jwtTokenProvider;
    private final UserFactory userFactory;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           AuthenticationManagerBuilder authenticationManagerBuilder,
                           JWTTokenProvider jwtTokenProvider,
                           UserFactory userFactory) {
        this.userRepository = userRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userFactory = userFactory;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
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
    @Override
    public User registerUser(RegisterDto registerDto) {
        try {
            User user = userFactory.ofRegisterDto(registerDto);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyRegisteredException("This email-address has already been registered! ");
        }
    }

    /*
     *@Return: new accessToken linked to logged in user, verified by authentication
     */
    @Override
    public JWTToken getNewJwtToken(String email, String password) {
        String jwt = jwtTokenProvider.createToken(getAuthentication(email, password));
        return new JWTToken(jwt);
    }

    /*
     *@Return: authentication verified through userName and password
     */
    private Authentication getAuthentication(String userName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, password);
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

    public User getUserByEmail(String name) {
        return userRepository.findUserByEmail(name)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s could not be found", name)));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s could not be found", id)));
    }
}
