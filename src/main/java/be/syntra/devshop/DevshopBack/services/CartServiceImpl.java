package be.syntra.devshop.DevshopBack.services;


import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.CartDto;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private UserServiceImpl userService;
    private DozerBeanMapper dozerMapper;

    @Autowired
    public CartServiceImpl(UserServiceImpl userService,
                           DozerBeanMapper dozerMapper) {
        this.userService = userService;
        this.dozerMapper = dozerMapper;
    }

    @Override
    public CartDto saveFinalizedCart(CartDto cartDto, Long userId) {
        User user = userService.getUserById(userId);
        user.getArchivedCarts().add(dozerMapper.map(cartDto, Cart.class));
        userService.save(user);
        return cartDto;
    }

}
