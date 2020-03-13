package be.syntra.devshop.DevshopBack.models;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class CartDto {
    private LocalDateTime cartCreationDateTime;
    private List<ProductDto> products;
    private boolean activeCart;
    private boolean finalizedCart;
    private boolean paidCart;
}
