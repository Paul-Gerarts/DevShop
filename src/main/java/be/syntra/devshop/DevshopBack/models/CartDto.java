package be.syntra.devshop.DevshopBack.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CartDto {
    private UserDto customer;
    private LocalDateTime cartCreationDateTime;
    private List<ProductDto> products;
    private boolean activeCart;
    private boolean finalizedCart;
    private boolean paidCart;
}
