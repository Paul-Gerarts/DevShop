package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Review;
import be.syntra.devshop.DevshopBack.models.ReviewDto;
import org.junit.jupiter.api.Test;

import static be.syntra.devshop.DevshopBack.testutilities.ReviewUtils.getDummyReviewDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewMapperTest {

    private ReviewMapper reviewMapper = new ReviewMapper();

    @Test
    void convertToReviewTest() {
        // given
        final ReviewDto dummyReviewDto = getDummyReviewDto();

        // when
        Review resultReview = reviewMapper.mapToReview(dummyReviewDto);

        // then
        assertEquals(dummyReviewDto.getReviewText(), resultReview.getReviewText());
    }
}
