package be.syntra.devshop.DevshopBack.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name", unique = true)
    @NotBlank
    private String name;

    @Column(name = "price")
    @NotNull
    @Digits(integer = 5, fraction = 2)
    @PositiveOrZero
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "PRODUCT_CART",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "cart_id")},
            foreignKey = @ForeignKey(name = "cart_fk"))
    private Cart cart;

    @NotEmpty
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "PRODUCT_CATEGORY",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "category_id")},
            foreignKey = @ForeignKey(name = "category_fk"))
    private List<Category> categories;

    @NotBlank
    private String description;

    private boolean archived;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
