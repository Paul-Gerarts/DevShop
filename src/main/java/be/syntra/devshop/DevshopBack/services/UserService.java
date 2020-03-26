package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;

import java.util.List;

public interface UserService {
    List<User> findAll();

    UserDto save(UserDto userDto);
}
