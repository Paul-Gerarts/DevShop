package be.syntra.devshop.DevshopBack.models;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CartDto {
    private User customer;
    private LocalDateTime cartCreationDateTime;
    private List<Product> products;
    private boolean activeCart;
    private boolean finalizedCart;
    private boolean paidCart;
}
