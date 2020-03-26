package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.exceptions.UserNotFoundException;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import org.springframework.stereotype.Service;

import static be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility.convertToCart;


@Service
public class CartServiceImpl implements CartService {
    private UserRepository userRepository;

    public CartServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public CartDto saveFinalizedCart(CartDto cartDto, Long userId) {
        User user = userRepository.findById(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s could not be found", userId)));

        user.getArchivedCarts()
                .add(convertToCart(cartDto));

        userRepository.save(user);

        return cartDto;
    }

}
