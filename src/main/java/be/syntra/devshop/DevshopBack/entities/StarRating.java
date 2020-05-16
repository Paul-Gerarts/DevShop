package be.syntra.devshop.DevshopBack.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "star_rating")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "star_rating_id")
    private Long id;

    @Column(name = "user_name", unique = true)
    @NotBlank
    private String userName;

    @Column(name = "rating")
    @PositiveOrZero
    private int rating;
}
