package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.StarRating;

import java.util.List;


public interface ProductService {

    Product save(Product product);

    List<Product> findAll();

    List<Product> findAllByCorrespondingCategory(Long id);

    void setNewCategory(Long categoryToDelete, Long categoryToSet);

    List<Product> findAllByArchivedFalse();

    List<Product> findAllByArchivedTrue();

    List<Product> findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest);

    Product findById(Long id);

    Double getProductRating(Long productId);

    Product submitRating(StarRating rating, Long productId);
}
