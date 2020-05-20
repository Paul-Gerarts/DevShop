package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.AddressUtils.createAddress;
import static be.syntra.devshop.DevshopBack.testutilities.AddressUtils.createAddressDto;
import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.*;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyNonArchivedProductList;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUser;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private CartMapper cartMapper;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    void convertToUserTest() {
        // given
        UserDto userDto = createUserDto();
        List<Product> dummyProductListFromUser = createDummyNonArchivedProductList();
        when(addressMapper.convertToAddress(any())).thenReturn(createAddress());
        when(cartMapper.convertToCart(any())).thenReturn(createCart());
        when(cartMapper.convertToCartList(any())).thenReturn(createDummyCartList());
        when(productMapper.convertToProductList(any())).thenReturn(dummyProductListFromUser);

        // when
        User mappedUser = userMapper.convertToUser(userDto);

        // then
        assertEquals(mappedUser.getClass(), User.class);
        assertEquals(mappedUser.getFirstName(), userDto.getFirstName());
        assertEquals(mappedUser.getLastName(), userDto.getLastName());
        assertEquals(mappedUser.getFullName(), userDto.getFullName());
        assertEquals(mappedUser.getAddress().getStreet(), userDto.getAddress().getStreet());
        assertEquals(mappedUser.getAddress().getNumber(), userDto.getAddress().getNumber());
        assertEquals(mappedUser.getAddress().getBoxNumber(), userDto.getAddress().getBoxNumber());
        assertEquals(mappedUser.getAddress().getPostalCode(), userDto.getAddress().getPostalCode());
        assertEquals(mappedUser.getAddress().getCity(), userDto.getAddress().getCity());
        assertEquals(mappedUser.getAddress().getCountry(), userDto.getAddress().getCountry());
        assertEquals(mappedUser.getArchivedCarts().get(0).getCartCreationDateTime().getHour(), userDto.getArchivedCarts().get(0).getCartCreationDateTime().getHour());
        assertEquals(mappedUser.getArchivedCarts().get(0).getCartCreationDateTime().getMinute(), userDto.getArchivedCarts().get(0).getCartCreationDateTime().getMinute());
        assertEquals(mappedUser.getArchivedCarts().get(0).getProducts().get(0).getName(), userDto.getArchivedCarts().get(0).getProducts().get(0).getName());
        assertEquals(mappedUser.getArchivedCarts().get(0).getProducts().get(0).getPrice(), userDto.getArchivedCarts().get(0).getProducts().get(0).getPrice());

    }

    @Test
    void convertToUserDtoTest() {
        // given
        User user = createUser();
        when(addressMapper.convertToAddressDto(any())).thenReturn(createAddressDto());
        when(cartMapper.convertToCartDto(any())).thenReturn(createCartDto());
        when(cartMapper.convertToCartDtoList(any())).thenReturn(createDummyCartDtoList());

        // when
        UserDto mappedUserDto = userMapper.convertToUserDto(user);

        // then
        assertEquals(mappedUserDto.getClass(), UserDto.class);
        assertEquals(mappedUserDto.getFirstName(), user.getFirstName());
        assertEquals(mappedUserDto.getLastName(), user.getLastName());
        assertEquals(mappedUserDto.getFullName(), user.getFullName());
        assertEquals(mappedUserDto.getAddress().getStreet(), user.getAddress().getStreet());
        assertEquals(mappedUserDto.getAddress().getNumber(), user.getAddress().getNumber());
        assertEquals(mappedUserDto.getAddress().getBoxNumber(), user.getAddress().getBoxNumber());
        assertEquals(mappedUserDto.getAddress().getPostalCode(), user.getAddress().getPostalCode());
        assertEquals(mappedUserDto.getAddress().getCity(), user.getAddress().getCity());
        assertEquals(mappedUserDto.getAddress().getCountry(), user.getAddress().getCountry());
        assertEquals(mappedUserDto.getArchivedCarts().get(0).getCartCreationDateTime().getHour(), user.getArchivedCarts().get(0).getCartCreationDateTime().getHour());
        assertEquals(mappedUserDto.getArchivedCarts().get(0).getCartCreationDateTime().getMinute(), user.getArchivedCarts().get(0).getCartCreationDateTime().getMinute());
    }
}
