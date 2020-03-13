package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class ProductUtils {

    public static ProductDto createProductDto() {
        return ProductDto.builder()
                .name("post-its")
                .price(BigDecimal.valueOf(1.00))
                .build();
    }

    public static Product createProduct() {
        return Product.builder()
                .name("post-its")
                .price(BigDecimal.valueOf(1.00))
                .build();
    }

    public static List<Product> createProductList() {
        List<Product> products = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            products.add(createProduct());
        }
        return products;
    }

}
