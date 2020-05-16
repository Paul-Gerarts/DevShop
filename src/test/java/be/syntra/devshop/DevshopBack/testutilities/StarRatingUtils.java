package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.StarRating;

import java.util.List;

public class StarRatingUtils {

    public static List<StarRating> createRatingList() {
        return List.of(
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
