package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProductFactoryTest {

    @Mock
    private ProductFactory productFactory;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void canCreateProductTest() {
        // given
        Product dummyProduct = createProduct();
        when(productFactory.of("test", new BigDecimal(5))).thenReturn(dummyProduct);

        // when
        Product resultProduct = productFactory.of("test", new BigDecimal(5));

        // then
        assertThat(resultProduct).isEqualTo(dummyProduct);
    }
}
