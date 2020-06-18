package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.ShopOrder;


public interface ShopOrderService {

    ShopOrder saveShopOrder(ShopOrder shopOrder, String email);
}
