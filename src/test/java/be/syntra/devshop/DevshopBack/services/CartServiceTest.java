package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Cart;
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

public class CartServiceTest {

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
        Cart dummyCart = createCart();
        User dummyUser = createUser();
        String name = "one";
        when(userService.getUserByEmail(name)).thenReturn(dummyUser);
        when(userService.save(dummyUser)).thenReturn(dummyUser);

        // when
        Cart resultCart = cartService.saveCartToArchivedCarts(dummyCart, name);

        // then
        assertEquals(dummyCart.getCartCreationDateTime(), resultCart.getCartCreationDateTime());
        assertEquals(dummyCart.getProducts().get(0).getName(), resultCart.getProducts().get(0).getName());
        assertEquals(dummyCart.getProducts().get(0).getPrice(), resultCart.getProducts().get(0).getPrice());
        assertEquals(dummyCart.getProducts().get(1).getName(), resultCart.getProducts().get(1).getName());
        assertEquals(dummyCart.getProducts().get(1).getPrice(), resultCart.getProducts().get(1).getPrice());
        assertEquals(dummyUser.getArchivedCarts().get(2).getProducts().get(0).getName(), dummyCart.getProducts().get(0).getName());
        assertEquals(dummyUser.getArchivedCarts().get(2).getProducts().get(0).getPrice(), dummyCart.getProducts().get(0).getPrice());

        verify(userService, times(1)).getUserByEmail(name);
        verify(userService, times(1)).save(any());

    }

}
