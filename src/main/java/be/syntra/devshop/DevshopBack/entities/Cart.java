package be.syntra.devshop.DevshopBack.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.annotations.CascadeType.ALL;

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
    @Column(name = "user")
    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    @NotNull
    @Column(name = "cartCreationDateTime")
    private LocalDateTime cartCreationDateTime;

    @NotNull
    @OneToOne(mappedBy = "cart", orphanRemoval = true)
    @Cascade(ALL)
    private List<Product> products;

    @NotNull
    @Column(name = "activeCart")
    private boolean activeCart;

    @NotNull
    @Column(name = "finalizedCart")
    private boolean finalizedCart;

    @NotNull
    @Column(name = "paidCart")
    private boolean paidCart;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customer=" + user +
                ", cartCreationDateTime=" + cartCreationDateTime +
                '}';
    }
}
