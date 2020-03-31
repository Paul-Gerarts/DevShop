package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductFactory {

    public Product of(
            String name,
            BigDecimal price
    ) {
        return Product.builder()
                .name(name)
                .price(price)
                .build();
    }

}
