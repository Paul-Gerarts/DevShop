package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.StarRating;

import java.util.Set;

public class StarRatingUtils {

    public static Set<StarRating> createRatingList() {
        return Set.of(
                createRating(),
                StarRating.builder()
                        .rating(1)
                        .userName("thomasf0n7a1n3@gmail.com")
                        .build(),
                StarRating.builder()
                        .rating(4)
                        .userName("paul.gerarts@juvo.be")
                        .build());
    }

    public static StarRating createRating() {
        return StarRating.builder()
                .rating(4)
                .userName("lens.huygh@gmail.com")
                .build();
    }
}
