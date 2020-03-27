package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.models.ProductDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.*;

public class CartUtils {


    public static Cart createActiveCart() {
        List<Product> products = createDummyProductList();
        return Cart.builder()
                .products(products)
                .cartCreationDateTime(LocalDateTime.of(2019, 3, 28, 14, 33, 48, 123456789))
                .activeCart(true)
                .finalizedCart(false)
                .paidCart(false)
                .build();
    }

    public static CartDto createCartDto() {
        List<ProductDto> products = createDummyProductDtoList();
        return CartDto.builder()
                .products(products)
                .cartCreationDateTime(LocalDateTime.of(2019, 3, 28, 14, 33, 48, 123456789))
                .activeCart(true)
                .finalizedCart(false)
                .paidCart(false)
                .build();
    }

    public static Cart createCartWithId() {
        List<Product> products = createDummyProductList();
        return Cart.builder()
                .id(1L)
                .products(products)
                .cartCreationDateTime(LocalDateTime.of(2019, 3, 28, 14, 33, 48, 123456789))
                .activeCart(true)
                .finalizedCart(false)
                .paidCart(false)
                .build();
    }

    public static List<Cart> createDummyCartList() {
        List<Cart> carts = new ArrayList<>();
        Cart cart1 = createActiveCart();
        Cart cart2 = Cart.builder()
                .cartCreationDateTime(LocalDateTime.of(2020, 3, 18, 14, 33, 48, 123456789))
                .products(createDummyProductList())
                .activeCart(false)
                .finalizedCart(true)
                .paidCart(true)
                .build();
        carts.add(cart1);
        carts.add(cart2);
        return carts;
    }

    public static List<CartDto> createDummyCartDtoList() {
        List<CartDto> carts = new ArrayList<>();
        CartDto cart1 = createCartDto();
        CartDto cart2 = CartDto.builder()
                .cartCreationDateTime(LocalDateTime.of(2020, 3, 18, 14, 33, 48, 123456789))
                .products(createProductDtoList())
                .activeCart(false)
                .finalizedCart(true)
                .paidCart(true)
                .build();
        carts.add(cart1);
        carts.add(cart2);
        return carts;
    }
}
