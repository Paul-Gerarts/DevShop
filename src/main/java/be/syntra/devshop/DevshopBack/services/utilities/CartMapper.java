package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.models.CartProductDto;
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

    public ShopOrder convertToCart(CartDto cartDto) {
        return ShopOrder.builder()
                .shopOrderCreationDateTime(cartDto.getCartCreationDateTime())
                .finalizedShopOrder(cartDto.isFinalizedCart())
                .paidShopOrder(cartDto.isPaidCart())
                .orderContents(convertToCartContentList(cartDto.getCartProductDtoList()))
                .build();
    }

    private List<OrderContent> convertToCartContentList(List<CartProductDto> cartProductDtoList) {
        return cartProductDtoList.stream()
                .map(this::convertToCartContent)
                .collect(Collectors.toList());
    }

    private OrderContent convertToCartContent(CartProductDto cartProductDto) {
        return OrderContent.builder()
                .product(productMapper.convertToProduct(cartProductDto.getProductDto()))
                .count(cartProductDto.getCount())
                .build();
    }

    List<ShopOrder> convertToCartList(List<CartDto> cartDtoList) {
        return cartDtoList.stream()
                .map(this::convertToCart)
                .collect(toUnmodifiableList());
    }
}
