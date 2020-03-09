package be.syntra.devshop.DevshopBack.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String street;

    @NotNull
    @NotBlank
    @Pattern(regexp = "\\d")
    private String number;

    @NotNull
    private String boxNumber;

    @NotNull
    @NotBlank
    @Pattern(regexp = "\\d")
    private String postalCode;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    private String province;

    @NotNull
    @NotBlank
    private String country;

    @OneToOne(mappedBy = "address", orphanRemoval = true)
    @Cascade(ALL)
    private Customer customer;

}
