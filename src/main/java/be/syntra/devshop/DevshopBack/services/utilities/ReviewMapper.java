package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Review;
import be.syntra.devshop.DevshopBack.models.ReviewDto;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public Review mapToReview(ReviewDto reviewDto) {
        return Review.builder()
                .reviewText(reviewDto.getReviewText())
                .userName(reviewDto.getUserName())
                .build();
    }
}
