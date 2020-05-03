package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapperUtility productMapperUtility;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapperUtility productMapperUtility
    ) {
        this.productRepository = productRepository;
        this.productMapperUtility = productMapperUtility;
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
