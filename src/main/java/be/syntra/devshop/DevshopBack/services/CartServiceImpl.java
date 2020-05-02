package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService {

    private final UserServiceImpl userService;

    @Autowired
    public CartServiceImpl(
            UserServiceImpl userService
    ) {
        this.userService = userService;
    }

    @Override
    public Cart saveCartToArchivedCarts(Cart cart, String email) {
        User user = userService.getUserByEmail(email);
        user.getArchivedCarts().add(cart);
        userService.save(user);
        return cart;
    }

}
