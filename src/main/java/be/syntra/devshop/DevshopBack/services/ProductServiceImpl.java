package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<Product> findAll() {
        return productRepository.findAll();
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
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("The product with id: " + id + " is not found"));
    }

    @Override
    public List<Product> findAllByArchivedFalse() {
        return productRepository.findAllByArchivedFalse();
    }

    @Override
    public List<Product> findAllByArchivedTrue() {
        return productRepository.findAllByArchivedTrue();
    }

    @Override
    public List<Product> findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest) {
        return productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest);
    }
}
