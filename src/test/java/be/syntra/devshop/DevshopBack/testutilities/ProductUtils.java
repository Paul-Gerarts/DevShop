package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.models.ProductPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategory_Headphones;
import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createMutableCategoryList;

public class ProductUtils {

    public static ProductDto createProductDto() {
        return ProductDto.builder()
                .name("post-its")
                .description("description")
                .archived(false)
                .price(BigDecimal.valueOf(1.00))
                .build();
    }

    public static Product createNonArchivedProduct() {
        return Product.builder()
                .id(1L)
                .name("post-its")
                .description("description")
                .archived(false)
                .price(BigDecimal.valueOf(1.00))
                .categories(List.of(createCategory_Headphones()))
                .build();
    }

    public static Product createArchivedProduct() {
        return Product.builder()
                .id(1L)
                .name("post-its")
                .description("description")
                .archived(true)
                .price(BigDecimal.valueOf(1.00))
                .categories(List.of(createCategory_Headphones()))
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

    public static List<Product> createDummyNonArchivedProductList() {
        Product product1 = Product.builder().name("test").categories(createMutableCategoryList()).price(new BigDecimal("55.99")).build();
        Product product2 = Product.builder().name("product").categories(createMutableCategoryList()).price(new BigDecimal("110.00")).build();
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        return productList;
    }

    public static List<ProductDto> createDummyProductDtoList() {
        ProductDto product1 = ProductDto.builder().name("test").price(new BigDecimal("55.99")).build();
        ProductDto product2 = ProductDto.builder().name("product").price(new BigDecimal("110.00")).build();
        List<ProductDto> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        return productList;
    }

    public static ProductList createDummyProductList(){
        return ProductList.builder().products(createDummyNonArchivedProductList()).build();
    }

    public static Page<Product> createDummyProductPage() {
        return new PageImpl<>(createProductList());
    }
    public static ProductPage createProductPage(){
        return ProductPage.builder()
                .productPage(createDummyProductPage())
                .minPrice(BigDecimal.ZERO)
                .maxPrice(BigDecimal.TEN)
                .build();
    }

    public static Pageable createDummyPageable(){
        return PageRequest.of(0, 10);
    }

}
