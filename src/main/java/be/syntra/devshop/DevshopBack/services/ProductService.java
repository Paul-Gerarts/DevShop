package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;


public interface ProductService {

    Product save(Product product);

    List<Product> findAllByCorrespondingCategory(Long id);

    void setNewCategory(Long categoryToDelete, Long categoryToSet);

    Page<Product> findAllByArchivedFalse(Pageable pageable);

    Page<Product> findAllByArchivedTrue(Pageable pageable);

    Page<Product> findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest, Pageable pageable);

    Product findById(Long id);

    Page<Product> findAllByDescriptionAndByArchivedFalse(String description, Pageable pageable);

    Page<Product> findAllByPriceBetween(BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable);

    Page<Product> findAllNonArchivedBySearchTermAndPriceBetween(String searchRequest,BigDecimal priceLow, BigDecimal PriceHigh, Pageable pageable);

    Page<Product> findAllNonArchivedByDescriptionAndPriceBetween(String description,BigDecimal priceLow, BigDecimal PriceHigh, Pageable pageable);
}
