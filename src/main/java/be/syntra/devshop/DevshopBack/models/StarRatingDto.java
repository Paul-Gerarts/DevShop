package be.syntra.devshop.DevshopBack.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarRatingDto {

    private String userName;
    private double rating;
}
