package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createNonArchivedProduct;
import static org.assertj.core.api.Assertions.assertThat;

public class ShopOrderFactoryTest {

    private final ShopOrderFactory shopOrderFactory = new ShopOrderFactory();

    @Test
    void canCreateCartTest() {
        // given
        Product dummyProduct = createNonArchivedProduct();
        boolean isFinalized = false;
        boolean isPaid = false;
        OrderContent dummyOrderContent = OrderContent.builder().count(2).product(dummyProduct).build();

        // when
        ShopOrder resultShopOrder = shopOrderFactory.of(List.of(dummyProduct), isFinalized, isPaid, List.of(dummyOrderContent));

        // then
        assertThat(resultShopOrder.getClass()).isEqualTo(ShopOrder.class);
        //assertThat(resultShopOrder.getProducts()).isEqualTo(List.of(dummyProduct));
        assertThat(resultShopOrder.isFinalizedShopOrder()).isEqualTo(isFinalized);
        assertThat(resultShopOrder.isPaidShopOrder()).isEqualTo(isPaid);
        assertThat(resultShopOrder.getOrderContents().get(0)).isEqualTo(dummyOrderContent);
    }
}
