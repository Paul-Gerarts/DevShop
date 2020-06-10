package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.models.CartContentDto;
import be.syntra.devshop.DevshopBack.models.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Component
public class CartMapper {
    private final ProductMapper productMapper;

    @Autowired
    public CartMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CartDto convertToCartDto(ShopOrder shopOrder) {
        return CartDto.builder()
                .cartCreationDateTime(shopOrder.getShopOrderCreationDateTime())
                //.products(shopOrder.getProducts())
                .finalizedCart(shopOrder.isFinalizedShopOrder())
                .paidCart(shopOrder.isPaidShopOrder())
                .build();
    }

    public ShopOrder convertToCart(CartDto cartDto) {
        return ShopOrder.builder()
                .shopOrderCreationDateTime(cartDto.getCartCreationDateTime())
                //.products(cartDto.getProducts())
                .finalizedShopOrder(cartDto.isFinalizedCart())
                .paidShopOrder(cartDto.isPaidCart())
                .orderContents(convertToCartContentList(cartDto.getCartContentDtoList()))
                .build();
    }

    private List<OrderContent> convertToCartContentList(List<CartContentDto> cartContentDtoList) {
        return cartContentDtoList.stream()
                .map(this::convertToCartContent)
                .collect(Collectors.toList());
    }

    private OrderContent convertToCartContent(CartContentDto cartContentDto) {
        return OrderContent.builder()
                //.productId(cartContentDto.getProductDto().getId())
                .product(productMapper.convertToProduct(cartContentDto.getProductDto()))
                .count(cartContentDto.getCount())
                .build();
    }


    public List<CartDto> convertToCartDtoList(List<ShopOrder> shopOrders) {
        return shopOrders.stream()
                .map(this::convertToCartDto)
                .collect(toUnmodifiableList());
    }

    public List<ShopOrder> convertToCartList(List<CartDto> cartDtoList) {
        return cartDtoList.stream()
                .map(this::convertToCart)
                .collect(toUnmodifiableList());
    }

    public ShopOrder convertToNewCart(CartDto cartDto) {
        return ShopOrder.builder()
                .shopOrderCreationDateTime(cartDto.getCartCreationDateTime())
                .paidShopOrder(cartDto.isPaidCart())
                .finalizedShopOrder(cartDto.isFinalizedCart())
                /*.orderContents(
                        cartDto.getProductDtos().stream()
                                .map(p ->
                                        OrderContent.builder()
                                                .productId(p.getId())
                                                .count(p.getTotalInCart())
                                                .build())
                                .collect(Collectors.toList()))*/
                .build();
    }
}
