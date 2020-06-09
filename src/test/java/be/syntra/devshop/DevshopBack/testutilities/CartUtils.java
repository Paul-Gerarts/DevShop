package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.CartContent;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.CartContentDto;
import be.syntra.devshop.DevshopBack.models.CartDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyNonArchivedProductList;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createProductDto;

public class CartUtils {


    public static Cart createCart() {
        List<Product> products = createDummyNonArchivedProductList();
        return Cart.builder()
                //.products(products)
                .cartContents(
                        products.stream().map(product -> CartContent.builder().productId(product.getId()).count(1).build()).collect(Collectors.toList())
                )
                .cartCreationDateTime(LocalDateTime.now())
                .finalizedCart(false)
                .paidCart(false)
                .build();
    }

    public static CartDto createCartDto() {
        List<Product> products = createDummyNonArchivedProductList();
        return CartDto.builder()
                .user("Someone")
                //.products(products)
                .cartContentDtoList(
                        //products.stream().map(product -> CartContentDto.builder().productId(product.getId()).count(1).build()).collect(Collectors.toList())
                        products.stream().map(product -> CartContentDto.builder().productDto(createProductDto()).count(1).build()).collect(Collectors.toList())
                )
                .cartCreationDateTime(LocalDateTime.now())
                .finalizedCart(false)
                .paidCart(false)
                .build();
    }

    public static Cart createCartWithId() {
        List<Product> products = createDummyNonArchivedProductList();
        return Cart.builder()
                .id(1L)
                //.products(products)
                .cartContents(
                        products.stream().map(product -> CartContent.builder().productId(product.getId()).count(1).build()).collect(Collectors.toList())
                )
                .cartCreationDateTime(LocalDateTime.now())
                .finalizedCart(false)
                .paidCart(false)
                .build();
    }

    public static List<Cart> createDummyCartList() {
        List<Cart> carts = new ArrayList<>();
        Cart cart1 = createCart();
        Cart cart2 = Cart.builder()
                .cartCreationDateTime(LocalDateTime.now())
                //.products(createDummyNonArchivedProductList())
                .cartContents(
                        createDummyNonArchivedProductList().stream().map(product -> CartContent.builder().productId(product.getId()).count(1).build()).collect(Collectors.toList())
                )
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
                .cartCreationDateTime(LocalDateTime.now())
                //.products(createDummyNonArchivedProductList())
                .cartContentDtoList(
                        createDummyNonArchivedProductList().stream().map(product -> CartContentDto.builder().productDto(createProductDto()).count(1).build()).collect(Collectors.toList())
                )
                .finalizedCart(true)
                .paidCart(true)
                .build();
        carts.add(cart1);
        carts.add(cart2);
        return carts;
    }
}
