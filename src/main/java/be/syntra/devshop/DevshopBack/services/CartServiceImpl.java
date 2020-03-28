package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import org.springframework.stereotype.Service;

import static be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility.convertToCart;


@Service
public class CartServiceImpl implements CartService {
    private UserRepository userRepository;
    private UserServiceImpl userService;

    public CartServiceImpl(UserRepository userRepository, UserServiceImpl userService) {
        this.userRepository = userRepository;

        this.userService = userService;
    }

    @Override
    public CartDto saveFinalizedCart(CartDto cartDto, Long userId) {
        User user = userService.getUserById(userId);
        user.getArchivedCarts()
                .add(convertToCart(cartDto));
        userRepository.save(user);

        return cartDto;
    }


}
