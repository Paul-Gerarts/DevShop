package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.repositories.StarRatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;
import java.util.stream.Collectors;

import static be.syntra.devshop.DevshopBack.testutilities.StarRatingUtils.createRatingList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class StarRatingServiceTest {

    @Mock
    private StarRatingRepository ratingRepository;

    @InjectMocks
    private StarRatingServiceImpl starRatingService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void canFindAllStarRatingsTest() {
        // given
        Set<StarRating> ratings = createRatingList();
        when(ratingRepository.findAll()).thenReturn(ratings.stream().collect(Collectors.toUnmodifiableList()));

        // when
        Set<StarRating> result = starRatingService.findAll();

        // then
        assertThat(result.stream().collect(Collectors.toUnmodifiableList()).get(0).getRating()).isEqualTo(4);
        assertThat(result.stream().collect(Collectors.toUnmodifiableList()).get(0).getUserName()).isEqualTo("lens.huygh@gmail.com");
        assertThat(result.size()).isEqualTo(ratings.size());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void canFindAverageRatingScoreForProductTest() {
        // given
        Set<StarRating> ratings = createRatingList();
        when(ratingRepository.getProductRating()).thenReturn(3D);

        // when
        Double result = starRatingService.getProductRating();
        Double doubleCheck = ratings.parallelStream()
                .mapToDouble(StarRating::getRating)
                .average()
                .orElse(Double.NaN);

        // then
        assertThat(result).isEqualTo(3D);
        assertThat(result).isEqualTo(doubleCheck);
        verify(ratingRepository, times(1)).getProductRating();
    }
}
