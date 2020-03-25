package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility.convertToUser;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDto save(UserDto userDto) {
        userRepository.save(convertToUser(userDto));
        return userDto;
    }
}
