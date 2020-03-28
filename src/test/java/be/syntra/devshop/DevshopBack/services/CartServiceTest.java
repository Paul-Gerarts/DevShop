package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.repositories.UserRepository;
import be.syntra.devshop.DevshopBack.services.utilities.CartMapperUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createCartDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CartServiceTest {
    @Mock
    private CartMapperUtility mapperUtility;

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
        Long userId = 1L;
        // when
        CartDto resultCartDto = cartService.saveFinalizedCart(dummyDto, userId);
        // then
        assertEquals(dummyDto.getCartCreationDateTime(), resultCartDto.getCartCreationDateTime());
        assertEquals(dummyDto.getProducts().get(0).getName(), resultCartDto.getProducts().get(0).getName());
        assertEquals(dummyDto.getProducts().get(0).getPrice(), resultCartDto.getProducts().get(0).getPrice());
        assertEquals(dummyDto.getProducts().get(1).getName(), resultCartDto.getProducts().get(1).getName());
        assertEquals(dummyDto.getProducts().get(1).getPrice(), resultCartDto.getProducts().get(1).getPrice());
        verify(userRepository, times(1)).save(any());
    }

}
