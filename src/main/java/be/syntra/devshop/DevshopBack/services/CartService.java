package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.CartDto;


public interface CartService {

    CartDto saveCartToArchivedCarts(CartDto cartDto, String name);
}
