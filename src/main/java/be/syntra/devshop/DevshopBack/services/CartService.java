package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Cart;


public interface CartService {

    Cart saveCartToArchivedCarts(Cart cart, String name);
}
