package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createCartDto;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUser;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

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
        when(userRepository.findById(userId)).thenReturn(ofNullable(dummyUser));
        when(userRepository.save(dummyUser)).thenReturn(dummyUser);
        // when
        CartDto resultCartDto = cartService.saveFinalizedCart(dummyDto, userId);
        // then
        assertEquals(dummyDto.getCartCreationDateTime(), resultCartDto.getCartCreationDateTime());
        assertEquals(dummyDto.getProducts().get(0).getName(), resultCartDto.getProducts().get(0).getName());
        assertEquals(dummyDto.getProducts().get(0).getPrice(), resultCartDto.getProducts().get(0).getPrice());
        assertEquals(dummyDto.getProducts().get(1).getName(), resultCartDto.getProducts().get(1).getName());
        assertEquals(dummyDto.getProducts().get(1).getPrice(), resultCartDto.getProducts().get(1).getPrice());
        verify(userRepository, times(1)).save(any());
        verify(userService, times(1)).getUserById(userId);
        verify(userRepository, times(1)).findById(userId);
    }

}
