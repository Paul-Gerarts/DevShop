package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.models.CartDto;

import java.util.List;

import static be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility.convertToProductDtoList;
import static be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility.convertToProductList;
import static java.util.stream.Collectors.toList;

public class CartMapperUtility {
    public static CartDto convertToCartDto(Cart cart) {
        return CartDto.builder()
                .cartCreationDateTime(cart.getCartCreationDateTime())
                .products(convertToProductDtoList(cart.getProducts()))
                .activeCart(cart.isActiveCart())
                .finalizedCart(cart.isFinalizedCart())
                .paidCart(cart.isPaidCart())
                .build();
    }

    public static Cart convertToCart(CartDto cartDto) {
        return Cart.builder()
                .cartCreationDateTime(cartDto.getCartCreationDateTime())
                .products(convertToProductList(cartDto.getProducts()))
                .activeCart(cartDto.isActiveCart())
                .finalizedCart(cartDto.isFinalizedCart())
                .paidCart(cartDto.isPaidCart())
                .build();
    }

    public static List<CartDto> convertToCartDtoList(List<Cart> carts) {
        return carts.stream()
                .map(CartMapperUtility::convertToCartDto)
                .collect(toList());
    }

    public static List<Cart> convertToCartList(List<CartDto> cartDtoList) {
        return cartDtoList.stream()
                .map(CartMapperUtility::convertToCart)
                .collect(toList());
    }
}
