package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
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
