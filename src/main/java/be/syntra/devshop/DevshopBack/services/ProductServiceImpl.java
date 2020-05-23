package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final PageRequest highestPricedProductPageRequest = PageRequest.of(0, 1, Sort.by("price").descending());
    private final PageRequest lowestPricedProductPageRequest = PageRequest.of(0, 1, Sort.by("price").ascending());

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
    public Page<Product> findAllByArchivedFalse(Pageable pageable) {
        return productRepository.findAllByArchivedFalse(pageable);
    }

    @Override
    public Page<Product> findMaxPriceProductByArchivedFalse() {
        return productRepository.findAllByArchivedFalse(highestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest, Pageable pageable) {
        return productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, pageable);
    }

    @Override
    public Page<Product> findMaxPriceProductByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest) {
        return productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, highestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findAllByArchivedTrue(Pageable pageable) {
        return productRepository.findAllByArchivedTrue(pageable);
    }

    @Override
    public Page<Product> findMaxPriceProductByArchivedTrue() {
        return productRepository.findAllByArchivedTrue(highestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findAllByDescriptionAndByArchivedFalse(String description, Pageable pageable) {
        return productRepository.findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(description, pageable);
    }

    @Override
    public Page<Product> findMaxPriceProductByDescriptionAndByArchivedFalse(String description) {
        return productRepository.findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(description, highestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findAllNonArchivedBySearchTermAndPriceBetween(String searchRequest, BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable) {
        return productRepository.findAllByNameContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(searchRequest, priceLow, priceHigh, pageable);
    }

    @Override
    public Page<Product> findMaxPriceProductNonArchivedBySearchTermAndPriceBetween(String searchRequest, BigDecimal priceLow, BigDecimal priceHigh) {
        return productRepository.findAllByNameContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(searchRequest, priceLow, priceHigh, highestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findAllNonArchivedByDescriptionAndPriceBetween(String description, BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable) {
        return productRepository.findAllByDescriptionContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(description, priceLow, priceHigh, pageable);
    }

    @Override
    public Page<Product> findMaxPriceProductNonArchivedByDescriptionAndPriceBetween(String description, BigDecimal priceLow, BigDecimal priceHigh) {
        return productRepository.findAllByDescriptionContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(description, priceLow, priceHigh, highestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findAllArchivedFalseByPriceBetween(BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable) {
        return productRepository.findAllByPriceIsBetweenAndArchivedFalse(priceLow, priceHigh, pageable);
    }

    @Override
    public Page<Product> findMaxPriceProductArchivedFalseByPriceBetween(BigDecimal priceLow, BigDecimal priceHigh) {
        return productRepository.findAllByPriceIsBetweenAndArchivedFalse(priceLow, priceHigh, highestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findMinPriceProductByArchivedFalse() {
        return productRepository.findAllByArchivedFalse(lowestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findMinPriceProductByArchivedTrue() {
        return productRepository.findAllByArchivedTrue(lowestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findMinPriceProductByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest) {
        return productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, lowestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findMinPriceProductByDescriptionAndByArchivedFalse(String description) {
        return productRepository.findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(description, lowestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findMinPriceProductNonArchivedBySearchTermAndPriceBetween(String searchRequest, BigDecimal priceLow, BigDecimal priceHigh) {
        return productRepository.findAllByNameContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(searchRequest, priceLow, priceHigh, lowestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findMinPriceProductNonArchivedByDescriptionAndPriceBetween(String description, BigDecimal priceLow, BigDecimal priceHigh) {
        return productRepository.findAllByDescriptionContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(description, priceLow, priceHigh, lowestPricedProductPageRequest);

    }

    @Override
    public Page<Product> findMinPriceProductArchivedFalseByPriceBetween(BigDecimal priceLow, BigDecimal priceHigh) {
        return productRepository.findAllByPriceIsBetweenAndArchivedFalse(priceLow, priceHigh, lowestPricedProductPageRequest);
    }

    @Override
    public Page<Product> findAllNonArchivedBySearchTermAndDescriptionAndPriceBetween(String searchTerm, String description, BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable) {
        return productRepository.findAllByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndPriceBetweenAndArchivedIsFalse(searchTerm, description, priceLow, priceHigh, pageable);
    }

    @Override
    public Page<Product> findAllBySearchModel(
            Pageable pageable,
            String searchRequest,
            String description,
            BigDecimal priceLow,
            BigDecimal priceHigh,
            boolean archived,
            List<String> selectedCategories,
            int amountOfSelectedCategories
    ) {
        return productRepository.findAllBySearchModel(
                pageable,
                searchRequest,
                description,
                priceLow,
                priceHigh,
                archived,
                selectedCategories,
                amountOfSelectedCategories);
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
        product.setRatings(update(product.getRatings(), rating));
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

    private Set<StarRating> update(Set<StarRating> ratings, StarRating rating) {
        Set<StarRating> allRatings = new HashSet<>();
        allRatings.addAll(ratings);
        allRatings.remove(rating);
        allRatings.add(rating);
        return allRatings;
    }
}
