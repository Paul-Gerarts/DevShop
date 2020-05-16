package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.repositories.StarRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarRatingServiceImpl implements StarRatingService {

    private final StarRatingRepository ratingRepository;

    @Autowired
    public StarRatingServiceImpl(
            StarRatingRepository ratingRepository
    ) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Double getProductRating() {
        return ratingRepository.getProductRating();
    }

    @Override
    public List<StarRating> findAll() {
        return ratingRepository.findAll();
    }
}
