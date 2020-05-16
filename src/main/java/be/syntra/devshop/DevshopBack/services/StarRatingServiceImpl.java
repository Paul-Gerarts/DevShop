package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.repositories.StarRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
    public Set<StarRating> findAll() {
        return new HashSet<>(ratingRepository.findAll());
    }
}
