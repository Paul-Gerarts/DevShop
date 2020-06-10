package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.models.CartContentDto;
import be.syntra.devshop.DevshopBack.models.CartDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyNonArchivedProductList;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createProductDto;

public class CartUtils {


    public static ShopOrder createCart() {
        List<Product> products = createDummyNonArchivedProductList();
        return ShopOrder.builder()
                //.products(products)
                .orderContents(
                        //products.stream().map(product -> OrderContent.builder().productId(product.getId()).count(1).build()).collect(Collectors.toList())
                        products.stream()
                                .map(product -> OrderContent.builder()
                                        //.productId(product.getId())
                                        .product(product)
                                        .count(1)
                                        .build())
                                .collect(Collectors.toList())
                )
                .shopOrderCreationDateTime(LocalDateTime.now())
                .finalizedShopOrder(false)
                .paidShopOrder(false)
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

    public static ShopOrder createCartWithId() {
        List<Product> products = createDummyNonArchivedProductList();
        return ShopOrder.builder()
                .id(1L)
                //.products(products)
                .orderContents(
                        //products.stream().map(product -> OrderContent.builder().productId(product.getId()).count(1).build()).collect(Collectors.toList())
                        products.stream()
                                .map(product -> OrderContent.builder()
                                        //.productId(product.getId())
                                        .product(product)
                                        .count(1)
                                        .build())
                                .collect(Collectors.toList())
                )
                .shopOrderCreationDateTime(LocalDateTime.now())
                .finalizedShopOrder(false)
                .paidShopOrder(false)
                .build();
    }

    public static List<ShopOrder> createDummyCartList() {
        List<ShopOrder> shopOrders = new ArrayList<>();
        ShopOrder shopOrder1 = createCart();
        ShopOrder shopOrder2 = ShopOrder.builder()
                .shopOrderCreationDateTime(LocalDateTime.now())
                //.products(createDummyNonArchivedProductList())
                .orderContents(
                        //createDummyNonArchivedProductList().stream().map(product -> OrderContent.builder().productId(product.getId()).count(1).build()).collect(Collectors.toList())
                        createDummyNonArchivedProductList().stream()
                                .map(product -> OrderContent.builder()
                                        //.productId(product.getId())
                                        .product(product)
                                        .count(1)
                                        .build())
                                .collect(Collectors.toList())
                )
                .finalizedShopOrder(true)
                .paidShopOrder(true)
                .build();
        shopOrders.add(shopOrder1);
        shopOrders.add(shopOrder2);
        return shopOrders;
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
