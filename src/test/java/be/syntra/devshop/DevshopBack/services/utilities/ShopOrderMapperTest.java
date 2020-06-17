package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.testutilities.CartUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyProductDtoList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class ShopOrderMapperTest {

    @InjectMocks
    private ShopOrderMapper shopOrderMapper;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    void convertToCartTest() {
        // given
        ShopOrder shopOrder = CartUtils.createCart();
        CartDto cartDto = CartUtils.createCartDto();
        cartDto.setCartCreationDateTime(shopOrder.getShopOrderCreationDateTime());
        List<ProductDto> dummyProductDtoList = createDummyProductDtoList();
        when(productMapper.convertToProductDtoList(any())).thenReturn(dummyProductDtoList);

        // when
        ShopOrder mappedShopOrder = shopOrderMapper.convertToShopOrder(cartDto);

        // then
        assertEquals(mappedShopOrder.getClass(), ShopOrder.class);
        assertEquals(mappedShopOrder.getShopOrderCreationDateTime().getDayOfWeek(), shopOrder.getShopOrderCreationDateTime().getDayOfWeek());
        assertEquals(mappedShopOrder.getShopOrderCreationDateTime().getHour(), shopOrder.getShopOrderCreationDateTime().getHour());
        assertEquals(mappedShopOrder.getShopOrderCreationDateTime().getMinute(), shopOrder.getShopOrderCreationDateTime().getMinute());
        assertEquals(mappedShopOrder.toString(), shopOrder.toString());
        assertEquals(mappedShopOrder.isFinalizedShopOrder(), cartDto.isFinalizedCart());
        assertEquals(mappedShopOrder.isPaidShopOrder(), cartDto.isPaidCart());
    }

    @Test
    void convertToCartList() {
        // given
        List<CartDto> dummyCartDtoList = CartUtils.createDummyCartDtoList();

        // when
        List<ShopOrder> mappedToShopOrderList = shopOrderMapper.convertToCartList(dummyCartDtoList);

        // then
        assertEquals(mappedToShopOrderList.get(0).getClass(), ShopOrder.class);
        assertEquals(dummyCartDtoList.size(), mappedToShopOrderList.size());
        assertEquals(mappedToShopOrderList.get(0).getShopOrderCreationDateTime(), dummyCartDtoList.get(0).getCartCreationDateTime());
        assertEquals(mappedToShopOrderList.get(1).getShopOrderCreationDateTime(), dummyCartDtoList.get(1).getCartCreationDateTime());
        assertEquals(mappedToShopOrderList.get(0).isFinalizedShopOrder(), dummyCartDtoList.get(0).isFinalizedCart());
        assertEquals(mappedToShopOrderList.get(1).isFinalizedShopOrder(), dummyCartDtoList.get(1).isFinalizedCart());
        assertEquals(mappedToShopOrderList.get(0).isPaidShopOrder(), dummyCartDtoList.get(0).isPaidCart());
        assertEquals(mappedToShopOrderList.get(1).isPaidShopOrder(), dummyCartDtoList.get(1).isPaidCart());
    }
}
