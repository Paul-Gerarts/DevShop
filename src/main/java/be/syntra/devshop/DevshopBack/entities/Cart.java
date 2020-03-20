package be.syntra.devshop.DevshopBack.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

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

    @NotNull
    @Column(name = "cartCreationDateTime")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")

    private LocalDateTime cartCreationDateTime;

    @NotNull
    @Column(name = "products")
    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
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
                ", cartCreationDateTime=" + cartCreationDateTime +
                '}';
    }
}
