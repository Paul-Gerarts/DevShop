package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.models.CategoryChangeDto;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapperUtility productMapperUtility;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductMapperUtility productMapperUtility,
            CategoryService categoryService
    ) {
        this.productRepository = productRepository;
        this.productMapperUtility = productMapperUtility;
        this.categoryService = categoryService;
    }

    @Override
    public ProductList findAll() {
        return productMapperUtility.convertToProductListObject(productRepository.findAll());
    }

    @Override
    public ProductList findAllByCorrespondingCategory(Long id) {
        return productMapperUtility.convertToProductListObject(productRepository.findAllWithCorrespondingCategory(id));
    }

    @Override
    public void setNewCategory(CategoryChangeDto categoryChangeDto) {
        List<Product> productsToChange = productRepository.findAllWithCorrespondingCategory(categoryChangeDto.getCategoryToDelete());
        Category categoryToSet = categoryService.findById(categoryChangeDto.getCategoryToSet());
        List<Category> newCategories = new ArrayList<>();
        newCategories.add(categoryToSet);
        productsToChange.forEach(product -> product.setCategories(newCategories));
        productRepository.saveAll(productsToChange);
    }

    @Override
    public ProductDto save(ProductDto productDTO) {
        productRepository.save(productMapperUtility.convertToProduct(productDTO));
        return productDTO;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("The product with id: " + id + " is not found"));
    }

    @Override
    public ProductList findAllByArchivedFalse() {
        return productMapperUtility.convertToProductListObject(productRepository.findAllByArchivedFalse());
    }

    @Override
    public ProductList findAllByArchivedTrue() {
        return productMapperUtility.convertToProductListObject(productRepository.findAllByArchivedTrue());
    }

    @Override
    public ProductList findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest) {
        return productMapperUtility.convertToProductListObject(productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest));
    }
}
