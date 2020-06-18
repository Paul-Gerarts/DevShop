package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class ShopOrderFactory {
    private final OrderContentFactory orderContentFactory;

    @Autowired
    public ShopOrderFactory(OrderContentFactory orderContentFactory) {
        this.orderContentFactory = orderContentFactory;
    }

    public ShopOrder of() {
        return ShopOrder.builder()
                .shopOrderCreationDateTime(LocalDateTime.now())
                .finalizedShopOrder(true)
                .paidShopOrder(true)
                .orderContents(getShopOrderContentList())
                .build();
    }

    private Set<OrderContent> getShopOrderContentList() {
        Set<OrderContent> orderContentSet = new HashSet<>();
        for (int i = 0; i < new Random().nextInt(10) + 1; i++) {
            orderContentSet.add(orderContentFactory.of());
        }
        return orderContentSet;
    }
}
