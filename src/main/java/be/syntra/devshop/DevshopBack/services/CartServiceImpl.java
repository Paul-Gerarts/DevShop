package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.repositories.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility.convertToCart;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartDto save(CartDto cartDto) {
        cartRepository.save(convertToCart(cartDto));
        return cartDto;
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }
}
