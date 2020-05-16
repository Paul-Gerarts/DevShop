package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.StarRating;

import java.util.List;

public interface StarRatingService {

    Double getProductRating();

    List<StarRating> findAll();
}
