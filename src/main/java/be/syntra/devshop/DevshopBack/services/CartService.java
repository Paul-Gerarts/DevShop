package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.models.CartDto;

import java.util.List;

public interface CartService {

    CartDto save(CartDto cartDto);

    List<Cart> findAll();
}
