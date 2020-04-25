package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        user.getArchivedCarts().add(cartMapperUtility.convertToCart(cartDto));
        userService.save(user);
        return cartDto;
    }

}
