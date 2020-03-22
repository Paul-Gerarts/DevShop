package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductFactory {

    public Product createProduct(
            String name,
            BigDecimal price,
            Cart cart
    ) {
        return Product.builder()
                .name(name)
                .price(price)
                .cart(cart)
                .build();
    }
}
