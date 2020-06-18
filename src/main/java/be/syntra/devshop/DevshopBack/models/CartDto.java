package be.syntra.devshop.DevshopBack.models;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDto {
    private String user;
    private LocalDateTime cartCreationDateTime;
    private boolean finalizedCart;
    private boolean paidCart;
    private Set<CartProductDto> cartProductDtoSet;
}
