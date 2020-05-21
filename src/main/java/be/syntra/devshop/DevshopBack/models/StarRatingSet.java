package be.syntra.devshop.DevshopBack.models;

import be.syntra.devshop.DevshopBack.entities.StarRating;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class StarRatingSet {

    private final Set<StarRating> ratings;
}
