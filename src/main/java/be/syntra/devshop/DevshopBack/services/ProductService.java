package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.CategoryChangeDto;

import java.util.List;


public interface ProductService {

    Product save(Product product);

    List<Product> findAll();

    List<Product> findAllByCorrespondingCategory(Long id);

    void setNewCategory(CategoryChangeDto categoryChangeDto);

    List<Product> findAllByArchivedFalse();

    List<Product> findAllByArchivedTrue();

    List<Product> findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest);

    Product findById(Long id);
}
