package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.models.StarRatingDto;
import be.syntra.devshop.DevshopBack.models.StarRatingSet;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StarRatingMapper {

    public StarRatingDto mapToDto(StarRating rating) {
        return StarRatingDto.builder()
                .rating(rating.getRating())
                .userName(rating.getUserName())
                .build();
    }

    public StarRating mapToStarRating(StarRatingDto starRatingDto) {
        return StarRating.builder()
                .rating(starRatingDto.getRating())
                .userName(starRatingDto.getUserName())
                .build();
    }

    public StarRatingSet mapToStarRatingSet(Set<StarRating> ratings) {
        return new StarRatingSet(ratings);
    }
}
