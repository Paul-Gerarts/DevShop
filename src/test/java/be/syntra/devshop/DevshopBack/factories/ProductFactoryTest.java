package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductFactoryTest {

    private ProductFactory productFactory = new ProductFactory();

    @Test
    void canCreateProductTest() {
        // given
        String productName = "test";
        BigDecimal productPrice = new BigDecimal(5);
        String productDescription = "description";
        boolean archived = false;

        // when
        Product resultProduct = productFactory.of(productName, productPrice, productDescription, archived);

        // then
        assertThat(resultProduct.getClass()).isEqualTo(Product.class);
        assertThat(resultProduct.getName()).isEqualTo(productName);
        assertThat(resultProduct.getPrice()).isEqualTo(productPrice);
        assertThat(resultProduct.isArchived()).isEqualTo(archived);
        assertThat(resultProduct.getDescription()).isEqualTo(productDescription);
    }
}
