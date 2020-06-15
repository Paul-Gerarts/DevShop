package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShopOrderServiceImpl implements ShopOrderService {

    private final UserServiceImpl userService;

    @Autowired
    public ShopOrderServiceImpl(
            UserServiceImpl userService
    ) {
        this.userService = userService;
    }

    @Override
    public ShopOrder saveShopOrder(ShopOrder shopOrder, String email) {
        User user = userService.getUserByEmail(email);
        user.getShopOrders().add(shopOrder);
        userService.save(user);
        return shopOrder;
    }

}
