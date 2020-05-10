package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public Page<Product> findAllByArchivedFalse(Pageable pageable) {
        return productRepository.findAllByArchivedFalse(pageable);
    }

    @Override
    public Page<Product> findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest, Pageable pageable) {
        return productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, pageable);
    }

    @Override
    public Page<Product> findAllByArchivedTrue(Pageable pageable) {
        return productRepository.findAllByArchivedTrue(pageable);
    }

    @Override
    public Page<Product> findAllByDescriptionAndByArchivedFalse(String description, Pageable pageable) {
        return productRepository.findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(description, pageable);
    }

    @Override
    public Page<Product> findAllByPriceBetween(BigDecimal priceHigh, BigDecimal priceLow, Pageable pageable) {
        return productRepository.findAllByPriceIsBetween(priceHigh, priceLow, pageable);
    }

    @Override
    public Page<Product> findAllNonArchivedBySearchTermAndPriceBetween(String searchRequest, BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable) {
        return productRepository.findAllByNameContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(searchRequest, priceLow, priceHigh, pageable);
    }

    @Override
    public Page<Product> findAllNonArchivedByDescriptionAndPriceBetween(String description, BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable) {
        return productRepository.findAllByDescriptionContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(description, priceLow, priceHigh, pageable);
    }

    @Override
    public Page<Product> findAllArchivedFalseByPriceBetween(BigDecimal priceLow, BigDecimal priceHigh, Pageable pageable) {
        return productRepository.findAllByPriceIsBetweenAndArchivedFalse(priceLow, priceHigh, pageable);
    }
}
