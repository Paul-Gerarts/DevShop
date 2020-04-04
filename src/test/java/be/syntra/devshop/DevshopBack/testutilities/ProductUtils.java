package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductUtils {

    public static ProductDto createProductDto() {
        return ProductDto.builder()
                .name("post-its")
                .description("description")
                .archived(false)
                .price(BigDecimal.valueOf(1.00))
                .build();
    }

    public static Product createProduct() {
        return Product.builder()
                .id(1L)
                .name("post-its")
                .price(BigDecimal.valueOf(1.00))
                .build();
    }

    public static Product createProductWithId() {
        return Product.builder()
                .id(1L)
                .name("post-its")
                .price(BigDecimal.valueOf(1.00))
                .build();
    }

    public static List<Product> createProductList() {
        List<Product> products = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            products.add(createProductWithId());
        }
        return products;
    }

    public static List<ProductDto> createProductDtoList() {
        List<ProductDto> products = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            products.add(createProductDto());
        }
        return products;
    }

    public static List<Product> createDummyProductList() {
        Product product1 = Product.builder().name("test").price(new BigDecimal("55.99")).build();
        Product product2 = Product.builder().name("product").price(new BigDecimal("110")).build();
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        return productList;
    }

    public static List<ProductDto> createDummyProductDtoList() {
        ProductDto product1 = ProductDto.builder().name("test").price(new BigDecimal("55.99")).build();
        ProductDto product2 = ProductDto.builder().name("product").price(new BigDecimal("110")).build();
        List<ProductDto> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        return productList;
    }
}
