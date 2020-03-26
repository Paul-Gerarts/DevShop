package be.syntra.devshop.DevshopBack.entities;

import be.syntra.devshop.DevshopBack.security.entities.UserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Column(name = "full_name")
    private String fullName;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Email
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "archived_carts")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "USER_CART",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "cart_id")},
            foreignKey = @ForeignKey(name = "cart_fk"))
    private List<Cart> archivedCarts;

    @Size(min = 1)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "USER_USERROLE",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "userrole_id", referencedColumnName = "userrole_id")},
            foreignKey = @ForeignKey(name = "userrole_fk"))
    private List<UserRole> userRoles;

    @OneToOne(cascade = CascadeType.ALL)
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
