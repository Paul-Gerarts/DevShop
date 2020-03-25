package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.repositories.CartRepository;
import org.springframework.stereotype.Service;

import static be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility.convertToCart;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartDto saveActiveCart(CartDto cartDto) {
        cartDto.setActiveCart(true);
        cartRepository.save(convertToCart(cartDto));
        return cartDto;
    }

}
