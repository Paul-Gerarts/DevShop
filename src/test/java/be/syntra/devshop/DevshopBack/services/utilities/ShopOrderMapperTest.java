package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.testutilities.CartUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyNonArchivedProductList;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyProductDtoList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class ShopOrderMapperTest {

    @InjectMocks
    private CartMapper cartMapper;

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
        ShopOrder mappedShopOrder = cartMapper.convertToCart(cartDto);

        // then
        assertEquals(mappedShopOrder.getClass(), ShopOrder.class);
        assertEquals(mappedShopOrder.getShopOrderCreationDateTime().getDayOfWeek(), shopOrder.getShopOrderCreationDateTime().getDayOfWeek());
        assertEquals(mappedShopOrder.getShopOrderCreationDateTime().getHour(), shopOrder.getShopOrderCreationDateTime().getHour());
        assertEquals(mappedShopOrder.getShopOrderCreationDateTime().getMinute(), shopOrder.getShopOrderCreationDateTime().getMinute());
        assertEquals(mappedShopOrder.toString(), shopOrder.toString());
        assertFalse(mappedShopOrder.isFinalizedShopOrder() && cartDto.isFinalizedCart());
        assertFalse(mappedShopOrder.isPaidShopOrder() && cartDto.isPaidCart());
    }

    @Test
    void convertToCartDtoTest() {
        // given
        ShopOrder shopOrder = CartUtils.createCartWithId();
        CartDto cartDto = CartUtils.createCartDto();
        List<ProductDto> dummyProductDtoList = createDummyProductDtoList();
        when(productMapper.convertToProductDtoList(any())).thenReturn(dummyProductDtoList);

        // when
        CartDto mappedCartDto = cartMapper.convertToCartDto(shopOrder);

        // then
        assertEquals(mappedCartDto.getClass(), CartDto.class);
        assertEquals(mappedCartDto.getCartCreationDateTime().getDayOfWeek(), cartDto.getCartCreationDateTime().getDayOfWeek());
        assertEquals(mappedCartDto.getCartCreationDateTime().getHour(), cartDto.getCartCreationDateTime().getHour());
        assertEquals(mappedCartDto.getCartCreationDateTime().getMinute(), cartDto.getCartCreationDateTime().getMinute());
        //assertEquals(mappedCartDto.getProducts().get(0).getName(), cartDto.getProducts().get(0).getName());
        assertFalse(mappedCartDto.isFinalizedCart() && shopOrder.isFinalizedShopOrder());
        assertFalse(mappedCartDto.isPaidCart() && shopOrder.isPaidShopOrder());
    }

    @Test
    void convertToCartDtoList() {
        // given
        List<ShopOrder> dummyShopOrderList = CartUtils.createDummyCartList();
        List<ProductDto> dummyProductDtoList = createDummyProductDtoList();
        when(productMapper.convertToProductDtoList(any())).thenReturn(dummyProductDtoList);

        // when
        List<CartDto> mappedToCartDtoList = cartMapper.convertToCartDtoList(dummyShopOrderList);

        // then
        assertEquals(mappedToCartDtoList.get(0).getClass(), CartDto.class);
        assertEquals(dummyShopOrderList.size(), mappedToCartDtoList.size());
        assertEquals(mappedToCartDtoList.get(0).getCartCreationDateTime(), dummyShopOrderList.get(0).getShopOrderCreationDateTime());
        assertEquals(mappedToCartDtoList.get(1).getCartCreationDateTime(), dummyShopOrderList.get(1).getShopOrderCreationDateTime());
        /*assertEquals(mappedToCartDtoList.get(0).getProducts().get(0).getName(), productMapper.convertToProductDtoList(dummyShopOrderList.get(0).getProducts()).get(0).getName());
        assertEquals(mappedToCartDtoList.get(1).getProducts().get(0).getPrice(), productMapper.convertToProductDtoList(dummyShopOrderList.get(1).getProducts()).get(0).getPrice());*/
        assertFalse(mappedToCartDtoList.get(0).isFinalizedCart() && dummyShopOrderList.get(0).isFinalizedShopOrder());
        assertTrue(mappedToCartDtoList.get(1).isFinalizedCart() && dummyShopOrderList.get(1).isFinalizedShopOrder());
        assertFalse(mappedToCartDtoList.get(0).isPaidCart() && dummyShopOrderList.get(0).isPaidShopOrder());
        assertTrue(mappedToCartDtoList.get(1).isPaidCart() && dummyShopOrderList.get(1).isPaidShopOrder());

    }

    @Test
    void convertToCartList() {
        // given
        List<CartDto> dummyCartDtoList = CartUtils.createDummyCartDtoList();
        List<Product> dummyProductList = createDummyNonArchivedProductList();
        when(productMapper.convertToProductList(any())).thenReturn(dummyProductList);

        // when
        List<ShopOrder> mappedToShopOrderList = cartMapper.convertToCartList(dummyCartDtoList);

        // then
        assertEquals(mappedToShopOrderList.get(0).getClass(), ShopOrder.class);
        assertEquals(dummyCartDtoList.size(), mappedToShopOrderList.size());
        assertEquals(mappedToShopOrderList.get(0).getShopOrderCreationDateTime(), dummyCartDtoList.get(0).getCartCreationDateTime());
        assertEquals(mappedToShopOrderList.get(1).getShopOrderCreationDateTime(), dummyCartDtoList.get(1).getCartCreationDateTime());
        /*assertEquals(mappedToShopOrderList.get(0).getProducts().get(0).getName(), dummyProductList.get(0).getName());
        assertEquals(mappedToShopOrderList.get(0).getProducts().get(1).getName(), dummyProductList.get(1).getName());
        assertEquals(mappedToShopOrderList.get(0).getProducts().get(0).getPrice(), dummyProductList.get(0).getPrice());
        assertEquals(mappedToShopOrderList.get(0).getProducts().get(1).getPrice(), dummyProductList.get(1).getPrice());*/
        assertFalse(mappedToShopOrderList.get(0).isFinalizedShopOrder() && dummyCartDtoList.get(0).isFinalizedCart());
        assertTrue(mappedToShopOrderList.get(1).isFinalizedShopOrder() && dummyCartDtoList.get(1).isFinalizedCart());
        assertFalse(mappedToShopOrderList.get(0).isPaidShopOrder() && dummyCartDtoList.get(0).isPaidCart());
        assertTrue(mappedToShopOrderList.get(1).isPaidShopOrder() && dummyCartDtoList.get(1).isPaidCart());
    }
}
