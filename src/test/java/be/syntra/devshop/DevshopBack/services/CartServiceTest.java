package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.CartDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility.convertToCart;
import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createCartDto;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveFinalizedCartTest() {
        // given
        CartDto dummyDto = createCartDto();
        User dummyUser = createUser();
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(dummyUser);

        // when
        CartDto resultCartDto = cartService.saveFinalizedCart(dummyDto, userId);

        // then
        assertEquals(dummyDto.getCartCreationDateTime(), resultCartDto.getCartCreationDateTime());
        assertEquals(dummyDto.getProducts().get(0).getName(), resultCartDto.getProducts().get(0).getName());
        assertEquals(dummyDto.getProducts().get(0).getPrice(), resultCartDto.getProducts().get(0).getPrice());
        assertEquals(dummyDto.getProducts().get(1).getName(), resultCartDto.getProducts().get(1).getName());
        assertEquals(dummyDto.getProducts().get(1).getPrice(), resultCartDto.getProducts().get(1).getPrice());
        assertEquals(dummyUser.getArchivedCarts().get(2).getCartCreationDateTime(), convertToCart(dummyDto).getCartCreationDateTime());
        assertEquals(dummyUser.getArchivedCarts().get(2).getProducts().get(0).getName(), convertToCart(dummyDto).getProducts().get(0).getName());
        assertEquals(dummyUser.getArchivedCarts().get(2).getProducts().get(0).getPrice(), convertToCart(dummyDto).getProducts().get(0).getPrice());
        verify(userService, times(1)).getUserById(userId);
        verify(userService, times(1)).save(any());


    }

}
