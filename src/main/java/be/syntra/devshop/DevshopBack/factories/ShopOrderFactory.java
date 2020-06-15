package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.OrderContent;
import be.syntra.devshop.DevshopBack.entities.ShopOrder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ShopOrderFactory {

    public ShopOrder of(
            boolean isFinalized,
            boolean isPaid,
            List<OrderContent> orderContentList
    ) {
        return ShopOrder.builder()
                .shopOrderCreationDateTime(LocalDateTime.now())
                .finalizedShopOrder(isFinalized)
                .paidShopOrder(isPaid)
                .orderContents(orderContentList)
                .build();
    }

}
