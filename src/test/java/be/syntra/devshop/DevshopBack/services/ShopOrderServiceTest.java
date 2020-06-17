package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.services.utilities.ShopOrderMapper;
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
    private ShopOrderMapper shopOrderMapper;

    @InjectMocks
    private ShopOrderServiceImpl cartService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveCartToArchivedCartsTest() {
        // given
        ShopOrder dummyShopOrder = createCart();
        User dummyUser = createUser();
        when(userService.getUserByEmail(dummyUser.getEmail())).thenReturn(dummyUser);
        when(userService.save(dummyUser)).thenReturn(dummyUser);

        // when
        ShopOrder resultShopOrder = cartService.saveShopOrder(dummyShopOrder, dummyUser.getEmail());

        // then
        assertEquals(dummyShopOrder.getShopOrderCreationDateTime(), resultShopOrder.getShopOrderCreationDateTime());

        assertEquals(dummyShopOrder.getOrderContents().iterator().next().getProduct().getName(), resultShopOrder.getOrderContents().iterator().next().getProduct().getName());
        assertEquals(dummyShopOrder.getOrderContents().iterator().next().getProduct().getPrice(), resultShopOrder.getOrderContents().iterator().next().getProduct().getPrice());
        assertEquals(dummyUser.getShopOrders().get(2).getOrderContents().iterator().next().getProduct().getName(), dummyShopOrder.getOrderContents().iterator().next().getProduct().getName());
        assertEquals(dummyUser.getShopOrders().get(2).getOrderContents().iterator().next().getProduct().getPrice(), dummyShopOrder.getOrderContents().iterator().next().getProduct().getPrice());

        verify(userService, times(1)).getUserByEmail(dummyUser.getEmail());
        verify(userService, times(1)).save(any());

    }

}
