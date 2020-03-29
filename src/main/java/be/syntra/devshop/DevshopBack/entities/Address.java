package be.syntra.devshop.DevshopBack.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @NotBlank
    @Column(name = "street")
    private String street;


    @NotBlank
    @Column(name = "number")
    @Pattern(regexp = "\\d*\\w[a-zA-Z]?")
    private String number;

    @NotNull
    @Column(name = "box_number")
    private String boxNumber;

    @NotBlank
    @Column(name = "postal_code")
    private String postalCode;

    @NotBlank
    @Column(name = "city")
    private String city;

    @NotBlank
    @Column(name = "country")
    private String country;

    @OneToOne(targetEntity = User.class, mappedBy = "address")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
