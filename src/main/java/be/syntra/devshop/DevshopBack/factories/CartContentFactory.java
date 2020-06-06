package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.CartContent;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class CartContentFactory {
    private ProductRepository productRepository;

    @Autowired
    public CartContentFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public CartContent of() {
        List<Long> productIdList = productRepository.findAll().stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        int productIdListLength = productIdList.size();
        return CartContent.builder()
                .productId(productIdList.get(new Random().nextInt(productIdListLength)))
                .count(new Random().nextInt(20) + 1)
                .build();
    }
}
