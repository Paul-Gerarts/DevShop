package be.syntra.devshop.DevshopBack.entities;

import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @NotBlank
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @NotBlank
    @Column(name = "password")
    private String password;


    @OneToOne
    @JoinColumn(name = "address_id")
    @RestResource(path = "userAddress", rel = "address")
    private Address address;

    @Column(name = "archived_carts")
    @ManyToMany
    @JoinTable(
            name = "USER_CART",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "cart_id")},
            foreignKey = @ForeignKey(name = "cart_fk"))
    private List<Cart> archivedCarts;


    @OneToOne
    private Cart activeCart;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                '}';
    }
}
