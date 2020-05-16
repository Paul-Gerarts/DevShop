package be.syntra.devshop.DevshopBack.repositories;

import be.syntra.devshop.DevshopBack.entities.StarRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StarRatingRepository extends JpaRepository<StarRating, Long> {

    @Query("SELECT AVG(s.rating) FROM StarRating s"
    )
    Double getProductRating();
}
