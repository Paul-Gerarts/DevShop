package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createActiveCart;
import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createCartDto;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private CartMapperUtility cartMapperUtility;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveCartToArchivedCartsTest() {
        // given
        CartDto dummyDto = createCartDto();
        Cart dummyCart = createActiveCart();
        User dummyUser = createUser();
        String name = "one";
        when(userService.getUserByName(name)).thenReturn(dummyUser);
        when(cartMapperUtility.convertToCart(dummyDto)).thenReturn(dummyCart);
        when(userService.save(dummyUser)).thenReturn(dummyUser);

        // when
        CartDto resultCartDto = cartService.saveCartToArchivedCarts(dummyDto, name);

        // then
        assertEquals(dummyDto.getCartCreationDateTime(), resultCartDto.getCartCreationDateTime());
        assertEquals(dummyDto.getProducts().get(0).getName(), resultCartDto.getProducts().get(0).getName());
        assertEquals(dummyDto.getProducts().get(0).getPrice(), resultCartDto.getProducts().get(0).getPrice());
        assertEquals(dummyDto.getProducts().get(1).getName(), resultCartDto.getProducts().get(1).getName());
        assertEquals(dummyDto.getProducts().get(1).getPrice(), resultCartDto.getProducts().get(1).getPrice());
        assertEquals(dummyUser.getArchivedCarts().get(2).getProducts().get(0).getName(), cartMapperUtility.convertToCart(dummyDto).getProducts().get(0).getName());
        assertEquals(dummyUser.getArchivedCarts().get(2).getProducts().get(0).getPrice(), cartMapperUtility.convertToCart(dummyDto).getProducts().get(0).getPrice());

        verify(userService, times(1)).getUserByName(name);
        verify(userService, times(1)).save(any());

    }

}
