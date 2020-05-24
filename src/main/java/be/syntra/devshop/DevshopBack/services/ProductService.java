package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


public interface ProductService {

    Product save(Product product);

    List<Product> findAllByCorrespondingCategory(Long id);

    void setNewCategory(Long categoryToDelete, Long categoryToSet);

    void removeOneCategory(Long id);

    Page<Product> findAllByArchivedFalse(Pageable pageable);

    Page<Product> findMaxPriceProductByArchivedFalse();

    Page<Product> findMinPriceProductByArchivedFalse();

    Page<Product> findAllByArchivedTrue(Pageable pageable);

    Page<Product> findMaxPriceProductByArchivedTrue();

    Page<Product> findMinPriceProductByArchivedTrue();

    Page<Product> findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest, Pageable pageable);

    Page<Product> findMaxPriceProductByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest);

    Page<Product> findMinPriceProductByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest);

    Product findById(Long id);

    Page<Product> findAllByDescriptionAndByArchivedFalse(String description, Pageable pageable);

    Page<Product> findMaxPriceProductByDescriptionAndByArchivedFalse(String description);

    Page<Product> findMinPriceProductByDescriptionAndByArchivedFalse(String description);

    Page<Product> findAllNonArchivedBySearchTermAndPriceBetween(String searchRequest, BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable);

    Page<Product> findMaxPriceProductNonArchivedBySearchTermAndPriceBetween(String searchRequest, BigDecimal priceLow, BigDecimal priceHigh);

    Page<Product> findMinPriceProductNonArchivedBySearchTermAndPriceBetween(String searchRequest, BigDecimal priceLow, BigDecimal priceHigh);

    Page<Product> findAllNonArchivedByDescriptionAndPriceBetween(String description, BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable);

    Page<Product> findMaxPriceProductNonArchivedByDescriptionAndPriceBetween(String description, BigDecimal priceLow, BigDecimal priceHigh);

    Page<Product> findMinPriceProductNonArchivedByDescriptionAndPriceBetween(String description, BigDecimal priceLow, BigDecimal priceHigh);

    Page<Product> findAllArchivedFalseByPriceBetween(BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable);

    Page<Product> findMaxPriceProductArchivedFalseByPriceBetween(BigDecimal priceLow, BigDecimal priceHigh);

    Page<Product> findMinPriceProductArchivedFalseByPriceBetween(BigDecimal priceLow, BigDecimal priceHigh);

    Page<Product> findAllNonArchivedBySearchTermAndDescriptionAndPriceBetween(String searchTerm, String description, BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable);

    Page<Product> findAllBySearchModel(
            Pageable pageable,
            String searchRequest,
            String description,
            BigDecimal priceLow,
            BigDecimal priceHigh,
            boolean archived,
            List<String> selectedCategories,
            int amountOfSelectedCategories);

    Double getProductRating(Long productId);

    Product submitRating(StarRating rating, Long productId);

    Set<StarRating> getAllRatingsFromProduct(Long productId);
}
