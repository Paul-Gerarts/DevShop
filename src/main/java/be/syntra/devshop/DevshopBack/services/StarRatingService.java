package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.StarRating;

import java.util.Set;

public interface StarRatingService {

    Double getProductRating();

    Set<StarRating> findAll();
}
