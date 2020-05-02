package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.security.controllers.dtos.RegisterDto;
import be.syntra.devshop.DevshopBack.security.entities.JWTToken;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);

    JWTToken getNewJwtToken(String userName, String password);

    User registerUser(RegisterDto registerDto);

    User getUserByEmail(String name);

    User getUserById(Long id);

}
