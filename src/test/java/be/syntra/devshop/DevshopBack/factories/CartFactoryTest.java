package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createActiveCart;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CartFactoryTest {

    @Mock
    private CartFactory cartFactory;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void canCreateCartTest() {
        // given
        Cart dummyCart = createActiveCart();
        Product dummyProduct = createProduct();
        when(cartFactory.of(List.of(dummyProduct), true, false, false)).thenReturn(dummyCart);

        // when
        Cart resultCart = cartFactory.of(List.of(dummyProduct), true, false, false);

        // then
        assertThat(resultCart).isEqualTo(dummyCart);
    }
}
