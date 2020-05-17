package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.models.StarRatingDto;
import org.springframework.stereotype.Component;

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
}
