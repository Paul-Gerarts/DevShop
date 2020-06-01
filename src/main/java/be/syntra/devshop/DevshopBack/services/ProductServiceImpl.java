package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.Review;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.models.SearchModel;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryService categoryService
    ) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Product> findAllByCorrespondingCategory(Long id) {
        return productRepository.findAllWithCorrespondingCategory(id);
    }

    @Override
    public void setNewCategory(Long categoryToDelete, Long categoryToSet) {
        List<Product> productsToChange = productRepository.findAllWithCorrespondingCategory(categoryToDelete);
        Category newCategoryToSet = categoryService.findById(categoryToSet);
        List<Category> newCategories = new ArrayList<>();
        newCategories.add(newCategoryToSet);
        productsToChange.forEach(product -> product.setCategories(newCategories));
        productRepository.saveAll(productsToChange);
    }

    @Override
    public Product save(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public void removeOneCategory(Long id) {
        List<Product> productsToChange = productRepository.findAllWithCorrespondingCategories(id);
        Category categoryToDelete = categoryService.findById(id);
        if (!productsToChange.isEmpty()) {
            productsToChange.forEach(product -> product.getCategories().remove(categoryToDelete));
            productRepository.saveAll(productsToChange);
        }
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("The product with id: " + id + " is not found"));
    }

    @Override
    public Page<Product> findAllByArchived(boolean archived, Pageable pageable) {
        return productRepository.findAllByArchived(archived, pageable);
    }

    @Override
    public Double findRoundedMaxPrice(boolean archived) {
        return productRepository.findRoundedMaxPrice(archived);
    }

    @Override
    public Page<Product> findAllBySearchModel(Pageable pageable, SearchModel searchModel) {
        return productRepository.findAllBySearchModel(
                pageable,
                searchModel.getSearchRequest(),
                searchModel.getDescription(),
                searchModel.getPriceLow(),
                searchModel.getPriceHigh(),
                searchModel.getAverageRating(),
                searchModel.isArchivedView(),
                searchModel.getSelectedCategories(),
                searchModel.getSelectedCategories().size());
    }

    @Override
    public Double getProductRating(Long productId) {
        return BigDecimal.valueOf(productRepository.getProductRating(productId).orElse(0D))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public Set<StarRating> getAllRatingsFromProduct(Long productId) {
        return productRepository.findAllStarRatingFromProduct(productId);
    }

    @Override
    public Product submitRating(StarRating rating, Long productId) {
        Product product = findById(productId);
        product.setRatings(updateStarRating(product.getRatings(), rating));
        product.setAverageRating(getAverageRating(product));
        productRepository.save(product);
        return product;
    }

    private double getAverageRating(Product product) {
        return product.getRatings()
                .parallelStream()
                .mapToDouble(StarRating::getRating)
                .average()
                .orElse(0D);
    }

    private Set<StarRating> updateStarRating(Set<StarRating> ratings, StarRating rating) {
        Set<StarRating> starRatings = new HashSet<>(ratings);
        starRatings.remove(rating);
        starRatings.add(rating);
        return starRatings;
    }

    @Override
    public Product submitReview(Review review, Long productId) {
        Product product = findById(productId);
        product.getReviews().add(review);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product removeReview(Review review, Long productId) {
        Product product = findById(productId);
        product.getReviews().remove(review);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product updateReview(Review review, Long productId) {
        Product product = findById(productId);
        product.setReviews(updateReviews(product.getReviews(), review));
        productRepository.save(product);
        return product;
    }

    private Set<Review> updateReviews(Set<Review> reviews, Review review) {
        Set<Review> reviewSet = new HashSet<>(reviews);
        reviewSet.remove(review);
        reviewSet.add(review);
        return reviewSet;
    }
}
