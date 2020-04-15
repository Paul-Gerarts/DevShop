package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;


public class CartServiceImpl implements CartService {

    private UserService userService;
    private CartMapperUtility cartMapperUtility;
    @Autowired
    public CartServiceImpl(UserService userService, CartMapperUtility cartMapperUtility) {
        this.userService = userService;
        this.cartMapperUtility = cartMapperUtility;
    }

    @Override
    public CartDto saveCartToArchivedCarts(CartDto cartDto, Long userId) {
        User user = userService.getUserById(userId);
        user.getArchivedCarts().add(cartMapperUtility.convertToCart(cartDto));
        userService.save(user);
        return cartDto;
    }

}
