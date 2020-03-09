package be.syntra.devshop.DevshopBack.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Customer customer;
    @NotNull
    private LocalDateTime cartCreationDateTime;
    @NotNull
    private List<Product> products;
    @NotNull
    private boolean activeCart;
    @NotNull
    private boolean finalizedCart;
    @NotNull
    private boolean paidCart;
}
