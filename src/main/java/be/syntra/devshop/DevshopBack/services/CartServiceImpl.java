package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.ShopOrder;
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
    public ShopOrder saveCartToArchivedCarts(ShopOrder shopOrder, String email) {
        User user = userService.getUserByEmail(email);
        user.getArchivedShopOrders().add(shopOrder);
        userService.save(user);
        return shopOrder;
    }

}
