package be.syntra.devshop.DevshopBack.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@ToString
public class CartDto {
    private LocalDateTime cartCreationDateTime;
    private List<ProductDto> products;
    private boolean activeCart;
    private boolean finalizedCart;
    private boolean paidCart;
}
