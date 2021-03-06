package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.models.StarRatingDto;
import be.syntra.devshop.DevshopBack.models.StarRatingSet;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static be.syntra.devshop.DevshopBack.testutilities.StarRatingUtils.createRatingSet;
import static org.assertj.core.api.Assertions.assertThat;

public class StarRatingMapperTest {

    private final StarRatingMapper starRatingMapper = new StarRatingMapper();

    @Test
    void canMapToDtoTest() {
        // given
        StarRating rating = StarRating.builder()
                .rating(4D)
                .userName("paul.gerarts@juvo.be")
                .build();

        // when
        StarRatingDto resultDto = starRatingMapper.mapToDto(rating);

        // then
        assertThat(resultDto.getRating()).isEqualTo(rating.getRating());
        assertThat(resultDto.getUserName()).isEqualTo(rating.getUserName());
    }

    @Test
    void canMapToStarRatingTest() {
        // given
        StarRatingDto rating = StarRatingDto.builder()
                .rating(4D)
                .userName("paul.gerarts@juvo.be")
                .build();

        // when
        StarRating result = starRatingMapper.mapToStarRating(rating);

        // then
        assertThat(result.getRating()).isEqualTo(rating.getRating());
        assertThat(result.getUserName()).isEqualTo(rating.getUserName());
    }

    @Test
    void canMapToStarRatingSet() {
        // given
        Set<StarRating> setOfStarRatings = createRatingSet();

        // when
        StarRatingSet result = starRatingMapper.mapToStarRatingSet(setOfStarRatings);

        // then
        assertThat(result.getRatings()).containsSequence(setOfStarRatings);
    }
}
