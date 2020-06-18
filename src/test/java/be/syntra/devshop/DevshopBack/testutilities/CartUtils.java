package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.models.CartProductDto;

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
                .orderContents(
                        products.stream()
                                .map(CartUtils::createOrderContentFromProduct)
                                .collect(Collectors.toSet()))
                .shopOrderCreationDateTime(LocalDateTime.now())
                .finalizedShopOrder(false)
                .paidShopOrder(false)
                .build();
    }

    public static CartDto createCartDto() {
        List<Product> products = createDummyNonArchivedProductList();
        return CartDto.builder()
                .user("Someone")
                .cartProductDtoSet(
                        products.stream()
                                .map(product -> CartProductDto.builder()
                                        .productDto(createProductDto())
                                        .count(1)
                                        .build())
                                .collect(Collectors.toSet()))
                .cartCreationDateTime(LocalDateTime.now())
                .finalizedCart(false)
                .paidCart(false)
                .build();
    }

    public static List<ShopOrder> createDummyCartList() {
        List<ShopOrder> shopOrders = new ArrayList<>();
        ShopOrder shopOrder1 = createCart();
        ShopOrder shopOrder2 = ShopOrder.builder()
                .shopOrderCreationDateTime(LocalDateTime.now())
                .orderContents(
                        createDummyNonArchivedProductList().stream()
                                .map(CartUtils::createOrderContentFromProduct)
                                .collect(Collectors.toSet()))
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
                .cartProductDtoSet(
                        createDummyNonArchivedProductList().stream()
                                .map(product -> CartProductDto.builder()
                                        .productDto(createProductDto())
                                        .count(1)
                                        .build())
                                .collect(Collectors.toSet()))
                .finalizedCart(true)
                .paidCart(true)
                .build();
        carts.add(cart1);
        carts.add(cart2);
        return carts;
    }

    private static OrderContent createOrderContentFromProduct(Product product) {
        return OrderContent.builder()
                .product(product)
                .count(1)
                .build();
    }
}
