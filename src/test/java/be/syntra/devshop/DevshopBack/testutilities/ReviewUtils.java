package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.Review;
import be.syntra.devshop.DevshopBack.models.ReviewDto;

import java.util.HashSet;
import java.util.Set;

public class ReviewUtils {

    public static ReviewDto getDummyReviewDto() {
        return ReviewDto.builder()
                .productId(1L)
                .userName("dummy@user.com")
                .reviewText("a dummy review")
                .build();
    }

    public static Review getDummyReview() {
        return Review.builder()
                .userName("dummy@user.com")
                .reviewText("a dummy review")
                .build();
    }

    public static Review getDummyOtherReview() {
        return Review.builder()
                .userName("user@dummy.org")
                .reviewText("a non realistic review")
                .build();
    }

    public static Set<Review> getReviewSetWithDummyReview() {
        return Set.of(getDummyReview());
    }

    public static Set<Review> getReviewSetWithDummyOtherReview() {
        Set<Review> reviewSet = new HashSet<>();
        reviewSet.add(getDummyOtherReview());
        return reviewSet;
    }
}
