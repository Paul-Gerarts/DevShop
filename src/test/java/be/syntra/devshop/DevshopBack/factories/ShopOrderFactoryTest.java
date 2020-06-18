package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createNonArchivedProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShopOrderFactoryTest {

    @InjectMocks
    ShopOrderFactory shopOrderFactory;

    @Mock
    OrderContentFactory orderContentFactory;

    @Test
    void canCreateCartTest() {
        // given
        Product dummyProduct = createNonArchivedProduct();
        OrderContent dummyOrderContent = OrderContent.builder().count(2).product(dummyProduct).build();
        when(orderContentFactory.of()).thenReturn(dummyOrderContent);

        // when
        ShopOrder resultShopOrder = shopOrderFactory.of();

        // then
        assertThat(resultShopOrder.getClass()).isEqualTo(ShopOrder.class);
        assertTrue(resultShopOrder.isFinalizedShopOrder());
        assertTrue(resultShopOrder.isPaidShopOrder());
        assertThat(resultShopOrder.getOrderContents().iterator().next()).isEqualTo(dummyOrderContent);
    }
}
