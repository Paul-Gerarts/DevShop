package be.syntra.devshop.DevshopBack.models;

import be.syntra.devshop.DevshopBack.entities.Customer;
import be.syntra.devshop.DevshopBack.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CartDto {
    private Customer customer;
    private LocalDateTime cartCreationDateTime;
    private List<Product> products;
    private boolean activeCart;
    private boolean finalizedCart;
    private boolean paidCart;
}
