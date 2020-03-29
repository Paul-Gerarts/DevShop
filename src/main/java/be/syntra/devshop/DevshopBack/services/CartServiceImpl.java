package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility.convertToCart;
import static be.syntra.devshop.DevshopBack.services.utilities.UserMapperUtility.convertToUserDto;


@Service
public class CartServiceImpl implements CartService {

    private UserServiceImpl userService;

    @Autowired
    public CartServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public CartDto saveFinalizedCart(CartDto cartDto, Long userId) {
        User user = userService.getUserById(userId);
        user.getArchivedCarts().add(convertToCart(cartDto));
        userService.save(convertToUserDto(user));
        return cartDto;
    }

}
