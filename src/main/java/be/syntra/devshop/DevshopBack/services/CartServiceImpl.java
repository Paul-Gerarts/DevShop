package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.repositories.CartRepository;
import be.syntra.devshop.DevshopBack.services.utilities.MapperUtility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private MapperUtility mapperUtility;

    public CartServiceImpl(CartRepository cartRepository, MapperUtility mapperUtility) {
        this.cartRepository = cartRepository;
        this.mapperUtility = mapperUtility;
    }

    @Override
    public CartDto save(CartDto cartDto) {
        cartRepository.save(mapperUtility.convertToCart(cartDto));
        return cartDto;
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }
}
