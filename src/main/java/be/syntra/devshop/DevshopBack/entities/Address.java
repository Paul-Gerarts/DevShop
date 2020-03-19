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

    @NotNull
    @NotBlank
    @Column(name = "street")
    private String street;

    @NotNull
    @NotBlank
    @Column(name = "number")
    @Pattern(regexp = "\\d")
    private String number;

    @NotNull
    @Column(name = "box_number")
    private String boxNumber;

    @NotNull
    @NotBlank
    @Column(name = "postal_code")
    @Pattern(regexp = "(\\d{4})")
    private String postalCode;

    @NotNull
    @NotBlank
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "province")
    private String province;

    @NotNull
    @NotBlank
    @Column(name = "country")
    private String country;

    @OneToOne(targetEntity = User.class, mappedBy = "address")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
