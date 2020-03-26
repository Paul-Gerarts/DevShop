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

        // when
        Product resultProduct = productFactory.of(productName, productPrice);

        // then
        assertThat(resultProduct.getClass()).isEqualTo(Product.class);
        assertThat(resultProduct.getName()).isEqualTo(productName);
        assertThat(resultProduct.getPrice()).isEqualTo(productPrice);
    }
}
