package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.models.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility.convertToCart;


@Service
public class CartServiceImpl implements CartService {

    private UserServiceImpl userService;

    @Autowired
    public CartServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public CartDto saveFinalizedCart(CartDto cartDto, Long userId) {
        userService.getUserById(userId).getArchivedCarts().add(convertToCart(cartDto));
        return cartDto;
    }

}
