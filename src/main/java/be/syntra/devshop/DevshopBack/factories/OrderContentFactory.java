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
        List<Product> productList = productRepository.findAll()/*.stream()
                .map(Product::getId)
                .collect(Collectors.toList())*/;
        int productListLength = productList.size();
        return OrderContent.builder()
                //.productId(productIdList.get(new Random().nextInt(productIdListLength)))
                .product(productList.get(new Random().nextInt(productListLength)))
                .count(new Random().nextInt(20) + 1)
                .build();
    }
}
