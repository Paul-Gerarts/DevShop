package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.testutilities.CartUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility.convertToProductDtoList;
import static be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility.convertToProductList;
import static org.junit.jupiter.api.Assertions.*;

public class CartMapperUtilityTest {
    private CartMapperUtility cartMapperUtility = new CartMapperUtility();

    @Test
    void convertToCartTest() {
        // given
        Cart cart = CartUtils.createActiveCart();
        CartDto cartDto = CartUtils.createCartDto();

        // when
        Cart mappedCart = CartMapperUtility.convertToCart(cartDto);

        // then
        assertEquals(mappedCart.getClass(), Cart.class);
        assertEquals(mappedCart.getCartCreationDateTime(), cart.getCartCreationDateTime());
        assertEquals(mappedCart.getProducts().toString(), cart.getProducts().toString());
        assertTrue(mappedCart.isActiveCart() && cartDto.isActiveCart());
        assertFalse(mappedCart.isFinalizedCart() && cartDto.isFinalizedCart());
        assertFalse(mappedCart.isPaidCart() && cartDto.isPaidCart());
    }

    @Test
    void convertToCartDtoTest() {
        // given
        Cart cart = CartUtils.createCartWithId();
        CartDto cartDto = CartUtils.createCartDto();

        // when
        CartDto mappedCartDto = CartMapperUtility.convertToCartDto(cart);

        // then
        assertEquals(mappedCartDto.getClass(), CartDto.class);
        assertEquals(mappedCartDto.getCartCreationDateTime(), cartDto.getCartCreationDateTime());
        assertEquals(mappedCartDto.getProducts(), cartDto.getProducts());
        assertTrue(cart.isActiveCart() && mappedCartDto.isActiveCart());
        assertFalse(mappedCartDto.isFinalizedCart() && cart.isFinalizedCart());
        assertFalse(mappedCartDto.isPaidCart() && cart.isPaidCart());
    }

    @Test
    void convertToCartDtoList() {
        // given
        List<Cart> dummyCartList = CartUtils.createDummyCartList();

        // when
        List<CartDto> mappedToCartDtoList = CartMapperUtility.convertToCartDtoList(dummyCartList);

        // then
        assertEquals(mappedToCartDtoList.get(0).getClass(), CartDto.class);
        assertEquals(dummyCartList.size(), mappedToCartDtoList.size());
        assertEquals(mappedToCartDtoList.get(0).getCartCreationDateTime(), dummyCartList.get(0).getCartCreationDateTime());
        assertEquals(mappedToCartDtoList.get(1).getCartCreationDateTime(), dummyCartList.get(1).getCartCreationDateTime());
        assertEquals(mappedToCartDtoList.get(0).getProducts().get(0).getName(), convertToProductDtoList(dummyCartList.get(0).getProducts()).get(0).getName());
        assertEquals(mappedToCartDtoList.get(1).getProducts().get(0).getPrice(), convertToProductDtoList(dummyCartList.get(1).getProducts()).get(0).getPrice());
        assertTrue(mappedToCartDtoList.get(0).isActiveCart() && dummyCartList.get(0).isActiveCart());
        assertFalse(mappedToCartDtoList.get(1).isActiveCart() && dummyCartList.get(1).isActiveCart());
        assertFalse(mappedToCartDtoList.get(0).isFinalizedCart() && dummyCartList.get(0).isFinalizedCart());
        assertTrue(mappedToCartDtoList.get(1).isFinalizedCart() && dummyCartList.get(1).isFinalizedCart());
        assertFalse(mappedToCartDtoList.get(0).isPaidCart() && dummyCartList.get(0).isPaidCart());
        assertTrue(mappedToCartDtoList.get(1).isPaidCart() && dummyCartList.get(1).isPaidCart());

    }

    @Test
    void convertToCartList() {
        // given
        List<CartDto> dummyCartDtoList = CartUtils.createDummyCartDtoList();

        // when
        List<Cart> mappedToCartList = CartMapperUtility.convertToCartList(dummyCartDtoList);

        // then
        assertEquals(mappedToCartList.get(0).getClass(), Cart.class);
        assertEquals(dummyCartDtoList.size(), mappedToCartList.size());
        assertEquals(mappedToCartList.get(0).getCartCreationDateTime(), dummyCartDtoList.get(0).getCartCreationDateTime());
        assertEquals(mappedToCartList.get(1).getCartCreationDateTime(), dummyCartDtoList.get(1).getCartCreationDateTime());
        assertEquals(mappedToCartList.get(0).getProducts().get(0).getName(), convertToProductList(dummyCartDtoList.get(0).getProducts()).get(0).getName());
        assertEquals(mappedToCartList.get(0).getProducts().get(1).getName(), convertToProductList(dummyCartDtoList.get(0).getProducts()).get(1).getName());
        assertEquals(mappedToCartList.get(0).getProducts().get(0).getPrice(), convertToProductList(dummyCartDtoList.get(0).getProducts()).get(0).getPrice());
        assertEquals(mappedToCartList.get(0).getProducts().get(1).getPrice(), convertToProductList(dummyCartDtoList.get(0).getProducts()).get(1).getPrice());
        assertTrue(mappedToCartList.get(0).isActiveCart() && dummyCartDtoList.get(0).isActiveCart());
        assertFalse(mappedToCartList.get(1).isActiveCart() && dummyCartDtoList.get(1).isActiveCart());
        assertFalse(mappedToCartList.get(0).isFinalizedCart() && dummyCartDtoList.get(0).isFinalizedCart());
        assertTrue(mappedToCartList.get(1).isFinalizedCart() && dummyCartDtoList.get(1).isFinalizedCart());
        assertFalse(mappedToCartList.get(0).isPaidCart() && dummyCartDtoList.get(0).isPaidCart());
        assertTrue(mappedToCartList.get(1).isPaidCart() && dummyCartDtoList.get(1).isPaidCart());

    }
}
