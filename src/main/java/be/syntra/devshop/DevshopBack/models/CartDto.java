package be.syntra.devshop.DevshopBack.models;


import be.syntra.devshop.DevshopBack.entities.Product;
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
    private List<Product> products;
    private boolean finalizedCart;
    private boolean paidCart;
}
