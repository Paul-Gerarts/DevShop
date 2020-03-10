package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import be.syntra.devshop.DevshopBack.services.utilities.MapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private MapperUtility mapperUtility;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MapperUtility mapperUtility) {
        this.userRepository = userRepository;
        this.mapperUtility = mapperUtility;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto save(UserDto userDto) {
        userRepository.save(mapperUtility.convertToUser(userDto));
        return userDto;
    }
}
