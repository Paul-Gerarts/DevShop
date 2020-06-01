package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.Review;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.models.SearchModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;


public interface ProductService {

    Product save(Product product);

    List<Product> findAllByCorrespondingCategory(Long id);

    void setNewCategory(Long categoryToDelete, Long categoryToSet);

    void removeOneCategory(Long id);

    Double findRoundedMaxPrice(boolean archived);

    Product findById(Long id);

    Page<Product> findAllByArchived(boolean archived, Pageable pageable);

    Page<Product> findAllBySearchModel(Pageable pageable, SearchModel searchModel);

    Double getProductRating(Long productId);

    Product submitRating(StarRating rating, Long productId);

    Set<StarRating> getAllRatingsFromProduct(Long productId);

    Product submitReview(Review review, Long productId);

    Product removeReview(Review review, Long productId);

    Product updateReview(Review review, Long productId);
}
