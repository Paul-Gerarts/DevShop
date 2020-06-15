package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.repositories.StarRatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;
import java.util.stream.Collectors;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createNonArchivedProduct;
import static be.syntra.devshop.DevshopBack.testutilities.StarRatingUtils.createRating;
import static be.syntra.devshop.DevshopBack.testutilities.StarRatingUtils.createRatingSet;
import static java.util.Optional.of;
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
        Set<StarRating> ratings = createRatingSet();
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
    void canGetRatingFromUserTest() {
        // given
        Product product = createNonArchivedProduct();
        StarRating rating = createRating();
        when(ratingRepository.getRatingFromUser(product.getId(), rating.getUserName())).thenReturn(of(rating));

        // when
        StarRating result = starRatingService.getRatingFromUser(product.getId(), rating.getUserName());

        // then
        assertThat(result.getRating()).isEqualTo(rating.getRating());
        assertThat(result.getUserName()).isEqualTo(rating.getUserName());
        verify(ratingRepository, times(1)).getRatingFromUser(product.getId(), rating.getUserName());
    }
}
