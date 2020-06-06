package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.CartContent;
import be.syntra.devshop.DevshopBack.entities.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createNonArchivedProduct;
import static org.assertj.core.api.Assertions.assertThat;

public class CartFactoryTest {

    private final CartFactory cartFactory = new CartFactory();

    @Test
    void canCreateCartTest() {
        // given
        Product dummyProduct = createNonArchivedProduct();
        boolean isFinalized = false;
        boolean isPaid = false;
        CartContent dummyCartContent = CartContent.builder().count(2).productId(2L).build();

        // when
        Cart resultCart = cartFactory.of(List.of(dummyProduct), isFinalized, isPaid, List.of(dummyCartContent));

        // then
        assertThat(resultCart.getClass()).isEqualTo(Cart.class);
        assertThat(resultCart.getProducts()).isEqualTo(List.of(dummyProduct));
        assertThat(resultCart.isFinalizedCart()).isEqualTo(isFinalized);
        assertThat(resultCart.isPaidCart()).isEqualTo(isPaid);
        assertThat(resultCart.getCartContents().get(0)).isEqualTo(dummyCartContent);
    }
}
