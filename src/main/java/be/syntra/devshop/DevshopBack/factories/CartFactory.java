package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CartFactory {

    public Cart of(
            List<Product> products,
            boolean isActive,
            boolean isFinalized,
            boolean isPaid
    ) {
        return Cart.builder()
                .cartCreationDateTime(LocalDateTime.now())
                .products(products)
                .activeCart(isActive)
                .finalizedCart(isFinalized)
                .paidCart(isPaid)
                .build();
    }
}
