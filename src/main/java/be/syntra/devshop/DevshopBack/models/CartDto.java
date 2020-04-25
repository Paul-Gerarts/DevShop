package be.syntra.devshop.DevshopBack.models;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDto {
    private String user;
    private LocalDateTime cartCreationDateTime;
    private List<ProductDto> products;
    private boolean activeCart;
    private boolean finalizedCart;
    private boolean paidCart;
}
