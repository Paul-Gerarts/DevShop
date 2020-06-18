package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@NoArgsConstructor
public class OrderContentFactory {
    private ProductRepository productRepository;

    @Autowired
    public OrderContentFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public OrderContent of() {
        List<Product> productList = productRepository.findAll();
        return OrderContent.builder()
                .product(productList.get(getNextRandomInt(productList.size())))
                .count(getNextRandomInt(20) + 1)
                .build();
    }

    private int getNextRandomInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }
}
