package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.User;
import be.syntra.devshop.DevshopBack.models.AddressDto;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.UserDto;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static be.syntra.devshop.DevshopBack.testutilities.CartUtils.createCartDto;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createNonArchivedProduct;
import static be.syntra.devshop.DevshopBack.testutilities.UserUtils.createUser;
import static org.assertj.core.api.Assertions.assertThat;

public class DozerMapperTest {

    private DozerBeanMapper dozerMapper;

    /*
     * The Dozer mapping engine is bi-directional, so no need to test both ways of mapping
     *  @See: https://www.baeldung.com/dozer
     */

    @BeforeEach
    public void setUp() {
        dozerMapper = new DozerBeanMapper();
    }

    @Test
    void canMapProductToProductDtoTest() {
        // given
        Product dummyProduct = createNonArchivedProduct();

        // when
        ProductDto resultProductDto = dozerMapper.map(dummyProduct, ProductDto.class);

        // then
        assertThat(dummyProduct.getName()).isEqualTo(resultProductDto.getName());
        assertThat(dummyProduct.getDescription()).isEqualTo(resultProductDto.getDescription());
        assertThat(dummyProduct.getPrice()).isEqualTo(resultProductDto.getPrice());
    }

    @Test
    void canMapCartDtoToCartTest() {
        // given
        CartDto dummyCartDto = createCartDto();

        // when
        Cart resultCart = dozerMapper.map(dummyCartDto, Cart.class);

        // then
        assertThat(dummyCartDto.getProducts().size()).isEqualTo(resultCart.getProducts().size());
        assertThat(dummyCartDto.getCartCreationDateTime()).isEqualTo(resultCart.getCartCreationDateTime());
    }

    @Test
    void canMapUserToUserDtoTest() {
        // given
        User dummyUser = createUser();

        // when
        UserDto resultUserDto = dozerMapper.map(dummyUser, UserDto.class);

        // then
        assertThat(resultUserDto.getAddress()).isEqualTo(dozerMapper.map(dummyUser.getAddress(), AddressDto.class));
        assertThat(resultUserDto.getArchivedCarts()).isEqualTo(dummyUser.getArchivedCarts());
        assertThat(resultUserDto.getActiveCart()).isEqualTo(dummyUser.getActiveCart());
        assertThat(resultUserDto.getFirstName()).isEqualTo(dummyUser.getFirstName());
        assertThat(resultUserDto.getLastName()).isEqualTo(dummyUser.getLastName());
        assertThat(resultUserDto.getFullName()).isEqualTo(dummyUser.getFullName());
    }
}
