package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.ShopOrder;


public interface CartService {

    ShopOrder saveCartToArchivedCarts(ShopOrder shopOrder, String name);
}
