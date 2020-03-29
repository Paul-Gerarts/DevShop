package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CartFactory {

    @Autowired
    private ProductFactory productFactory;

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

    public Cart ofEmptyCart() {
        return Cart.builder()
                .cartCreationDateTime(LocalDateTime.now())
                .products(List.of(productFactory.ofEmptyProduct()))
                .activeCart(false)
                .finalizedCart(false)
                .paidCart(false)
                .build();
    }
}
