package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.CartDto;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    CartDto saveCartToArchivedCarts(CartDto cartDto, Long userId);
}
