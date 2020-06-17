package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ShopOrderFactory {
    private final OrderContentFactory orderContentFactory;

    @Autowired
    public ShopOrderFactory(OrderContentFactory orderContentFactory) {
        this.orderContentFactory = orderContentFactory;
    }

    public ShopOrder of(
    ) {
        return ShopOrder.builder()
                .shopOrderCreationDateTime(LocalDateTime.now())
                .finalizedShopOrder(true)
                .paidShopOrder(true)
                .orderContents(getShopOrderContentList())
                .build();
    }

    private List<OrderContent> getShopOrderContentList() {
        List<OrderContent> orderContentList = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(10) + 1; i++) {
            orderContentList.add(orderContentFactory.of());
        }
        return orderContentList;
    }

}
