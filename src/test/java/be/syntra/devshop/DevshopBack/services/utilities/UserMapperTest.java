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
import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createCart;
import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createDummyCartList;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyNonArchivedProductList;
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
    private ShopOrderMapper shopOrderMapper;

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
        when(shopOrderMapper.convertToShopOrder(any())).thenReturn(createCart());
        when(shopOrderMapper.convertToCartList(any())).thenReturn(createDummyCartList());
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
        assertEquals(mappedUser.getShopOrders().get(0).getShopOrderCreationDateTime().getHour(), userDto.getArchivedCarts().get(0).getCartCreationDateTime().getHour());
        assertEquals(mappedUser.getShopOrders().get(0).getShopOrderCreationDateTime().getMinute(), userDto.getArchivedCarts().get(0).getCartCreationDateTime().getMinute());
        assertEquals(mappedUser.getShopOrders().size(), userDto.getArchivedCarts().size());
    }
}
