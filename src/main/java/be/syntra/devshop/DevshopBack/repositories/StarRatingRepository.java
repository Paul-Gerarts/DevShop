package be.syntra.devshop.DevshopBack.repositories;

import be.syntra.devshop.DevshopBack.entities.StarRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StarRatingRepository extends JpaRepository<StarRating, Long> {

    @Query(value = "SELECT sr.STAR_RATING_ID, sr.RATING, sr.USER_NAME FROM PRODUCT_STAR_RATING psr "
            + "LEFT JOIN STAR_RATING sr ON psr.STAR_RATING_ID = sr.STAR_RATING_ID "
            + "WHERE psr.PRODUCT_ID = :productId "
            + "AND sr.USER_NAME = :userName",
            nativeQuery = true
    )
    Optional<StarRating> getRatingFromUser(
            @Param("productId") Long productId,
            @Param("userName") String userName
    );
}
