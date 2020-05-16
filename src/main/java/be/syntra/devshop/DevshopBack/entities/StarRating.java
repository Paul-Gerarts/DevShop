package be.syntra.devshop.DevshopBack.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

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

    @Column(name = "user_name")
    @NotBlank
    private String userName;

    @Column(name = "rating")
    @PositiveOrZero
    private int rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarRating that = (StarRating) o;
        return getUserName().equals(that.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName());
    }
}
