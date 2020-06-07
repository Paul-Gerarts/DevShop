package be.syntra.devshop.DevshopBack.models;

import be.syntra.devshop.DevshopBack.entities.Review;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private boolean archived;
    private List<String> categoryNames;
    private Set<StarRating> ratings;
    private Double averageRating;
    private int totalInCart;
    private Set<Review> reviews;

}
