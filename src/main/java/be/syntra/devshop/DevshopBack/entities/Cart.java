package be.syntra.devshop.DevshopBack.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(name = "cart_id")
    private Long id;

    @NotNull
    @Column(name = "cart_creation_date_time")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime cartCreationDateTime;

    @Column(name = "products")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "PRODUCT_CART",
            joinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "cart_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "product_id")},
            foreignKey = @ForeignKey(name = "cart_fk"))
    private List<Product> products;

    @Column(name = "active_cart")
    private boolean activeCart;

    @Column(name = "finalized_cart")
    private boolean finalizedCart;

    @Column(name = "paid_cart")
    private boolean paidCart;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", cartCreationDateTime=" + cartCreationDateTime +
                '}';
    }
}
