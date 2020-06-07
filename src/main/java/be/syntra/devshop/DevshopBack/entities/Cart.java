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

    /*@Size(min = 1)
    @Column(name = "products")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "CART_PRODUCT",
            joinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "cart_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "product_id")},
            foreignKey = @ForeignKey(name = "product_fk"))
    private List<Product> products;*/

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

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinTable(
            name = "CART_CONTENTS",
            joinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "cart_id")},
            inverseJoinColumns = {@JoinColumn(name = "cart_content_id", referencedColumnName = "cart_content_id")},
            foreignKey = @ForeignKey(name = "cart_detail_fk"))
    private List<CartContent> cartContents;
}
