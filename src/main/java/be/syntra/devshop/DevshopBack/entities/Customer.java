package be.syntra.devshop.DevshopBack.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "first name")
    private String firstName;

    @NotNull
    @NotBlank
    @Column(name = "last name")
    private String lastName;

    @NotNull
    @NotBlank
    @Column(name = "full name")
    private String fullName;

    @NotNull
    @NotBlank
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "address")
    @OneToOne(mappedBy = "customer", orphanRemoval = true)
    @Cascade(ALL)
    private Address address;

    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    @Cascade(ALL)
    private List<Cart> archivedCarts = new ArrayList<>();

    @NotNull
    @OneToOne(mappedBy = "customer", orphanRemoval = true)
    @Cascade(ALL)
    private Cart activeCart = new Cart();

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
