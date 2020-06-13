package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.services.utilities.CartMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createCart;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShopOrderServiceTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveCartToArchivedCartsTest() {
        // given
        ShopOrder dummyShopOrder = createCart();
        User dummyUser = createUser();
        String name = "one";
        when(userService.getUserByEmail(name)).thenReturn(dummyUser);
        when(userService.save(dummyUser)).thenReturn(dummyUser);

        // when
        ShopOrder resultShopOrder = cartService.saveCartToArchivedCarts(dummyShopOrder, name);

        // then
        assertEquals(dummyShopOrder.getShopOrderCreationDateTime(), resultShopOrder.getShopOrderCreationDateTime());

        assertEquals(dummyShopOrder.getOrderContents().get(0).getProduct().getName(), resultShopOrder.getOrderContents().get(0).getProduct().getName());
        assertEquals(dummyShopOrder.getOrderContents().get(0).getProduct().getPrice(), resultShopOrder.getOrderContents().get(0).getProduct().getPrice());
        assertEquals(dummyShopOrder.getOrderContents().get(1).getProduct().getName(), resultShopOrder.getOrderContents().get(1).getProduct().getName());
        assertEquals(dummyShopOrder.getOrderContents().get(1).getProduct().getPrice(), resultShopOrder.getOrderContents().get(1).getProduct().getPrice());
        assertEquals(dummyUser.getArchivedShopOrders().get(2).getOrderContents().get(0).getProduct().getName(), dummyShopOrder.getOrderContents().get(0).getProduct().getName());
        assertEquals(dummyUser.getArchivedShopOrders().get(2).getOrderContents().get(0).getProduct().getPrice(), dummyShopOrder.getOrderContents().get(0).getProduct().getPrice());

        verify(userService, times(1)).getUserByEmail(name);
        verify(userService, times(1)).save(any());

    }

}
