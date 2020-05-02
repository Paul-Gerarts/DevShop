package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Product> findAll() {
        return productRepository.findAll();
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
    public List<Product> findAllByArchivedFalse() {
        return productRepository.findAllByArchivedFalse();
    }

    @Override
    public List<Product> findAllByArchivedTrue() {
        return productRepository.findAllByArchivedTrue();
    }

    @Override
    public ProductList findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest) {
        return productMapperUtility.convertToProductListObject(productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest));
    }

    @Override
    public List<Product> findAllBySearchModel() {
        return null;
    }
}
