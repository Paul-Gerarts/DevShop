package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private UserServiceImpl userService;
    private CartMapperUtility cartMapperUtility;

    @Autowired
    public CartServiceImpl(UserServiceImpl userService,
                           CartMapperUtility cartMapperUtility) {
        this.userService = userService;
        this.cartMapperUtility = cartMapperUtility;
    }

    @Override
    public CartDto saveFinalizedCart(CartDto cartDto, Long userId) {
        User user = userService.getUserById(userId);
        user.getArchivedCarts().add(cartMapperUtility.convertToCart(cartDto));
        userService.save(user);
        return cartDto;
    }

}
