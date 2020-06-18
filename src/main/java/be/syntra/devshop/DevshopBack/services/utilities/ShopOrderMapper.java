package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import be.syntra.devshop.DevshopBack.models.CartDto;
import be.syntra.devshop.DevshopBack.models.CartProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Component
public class ShopOrderMapper {
    private final ProductMapper productMapper;

    @Autowired
    public ShopOrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public ShopOrder convertToShopOrder(CartDto cartDto) {
        return ShopOrder.builder()
                .shopOrderCreationDateTime(cartDto.getCartCreationDateTime())
                .finalizedShopOrder(cartDto.isFinalizedCart())
                .paidShopOrder(cartDto.isPaidCart())
                .orderContents(convertToOrderContentList(cartDto.getCartProductDtoSet()))
                .build();
    }

    private Set<OrderContent> convertToOrderContentList(Set<CartProductDto> cartProductDtoList) {
        return cartProductDtoList.stream()
                .map(this::convertToOrderContent)
                .collect(Collectors.toSet());
    }

    private OrderContent convertToOrderContent(CartProductDto cartProductDto) {
        return OrderContent.builder()
                .product(productMapper.convertToProduct(cartProductDto.getProductDto()))
                .count(cartProductDto.getCount())
                .build();
    }

    public List<ShopOrder> convertToCartList(List<CartDto> cartDtoList) {
        return cartDtoList.stream()
                .map(this::convertToShopOrder)
                .collect(toUnmodifiableList());
    }
}
