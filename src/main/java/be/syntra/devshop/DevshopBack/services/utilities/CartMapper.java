package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.models.CartDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Component
public class CartMapper {


    public CartDto convertToCartDto(Cart cart) {
        return CartDto.builder()
                .cartCreationDateTime(cart.getCartCreationDateTime())
                .products(cart.getProducts())
                .activeCart(cart.isActiveCart())
                .finalizedCart(cart.isFinalizedCart())
                .paidCart(cart.isPaidCart())
                .build();
    }

    public Cart convertToCart(CartDto cartDto) {
        return Cart.builder()
                .cartCreationDateTime(cartDto.getCartCreationDateTime())
                .products(cartDto.getProducts())
                .activeCart(cartDto.isActiveCart())
                .finalizedCart(cartDto.isFinalizedCart())
                .paidCart(cartDto.isPaidCart())
                .build();
    }

    public List<CartDto> convertToCartDtoList(List<Cart> carts) {
        return carts.stream()
                .map(this::convertToCartDto)
                .collect(toUnmodifiableList());
    }

    public List<Cart> convertToCartList(List<CartDto> cartDtoList) {
        return cartDtoList.stream()
                .map(this::convertToCart)
                .collect(toUnmodifiableList());
    }

}
