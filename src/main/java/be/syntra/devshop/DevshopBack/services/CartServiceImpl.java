package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final UserServiceImpl userService;
    private final CartMapperUtility cartMapperUtility;

    @Autowired
    public CartServiceImpl(
            UserServiceImpl userService,
            CartMapperUtility cartMapperUtility
    ) {
        this.userService = userService;
        this.cartMapperUtility = cartMapperUtility;
    }

    @Override
    public CartDto saveCartToArchivedCarts(CartDto cartDto, String name) {
        User user = userService.getUserByName(name);
        List<Cart> userArchivedCart = new ArrayList<>();
        userArchivedCart.add(cartMapperUtility.convertToCart(cartDto));
        user.setArchivedCarts(userArchivedCart);
        userService.save(user);
        return cartDto;
    }

}
