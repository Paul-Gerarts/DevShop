package be.syntra.devshop.DevshopBack.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @OneToOne(targetEntity = User.class, mappedBy = "activeCart")
    @JsonIgnore
    private User user;


    @Column(name = "cart_creation_date_time")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime cartCreationDateTime;


    @Column(name = "products")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    @NotNull
    @Column(name = "active_cart")
    private boolean activeCart;

    @NotNull
    @Column(name = "finalized_cart")
    private boolean finalizedCart;

    @NotNull
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
