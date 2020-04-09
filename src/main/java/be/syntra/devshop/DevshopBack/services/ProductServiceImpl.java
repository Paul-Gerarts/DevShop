package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private DozerBeanMapper dozerMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, DozerBeanMapper dozerBeanMapper) {
        this.productRepository = productRepository;
        this.dozerMapper = dozerBeanMapper;
    }

    @Override
    public ProductList findAll() {
        return dozerMapper.map(productRepository.findAll(), ProductList.class);
    }

    @Override
    public ProductDto save(ProductDto productDTO) {
        productRepository.save(dozerMapper.map(productDTO, Product.class));
        return productDTO;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("The product with id: " + id + " is not found"));
    }

    @Override
    public ProductList findAllByArchivedFalse() {
        return dozerMapper.map(productRepository.findAllByArchivedFalse(), ProductList.class);
    }

    @Override
    public ProductList findAllByArchivedTrue() {
        return dozerMapper.map(productRepository.findAllByArchivedTrue(), ProductList.class);
    }

    @Override
    public ProductList findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest) {
        return dozerMapper.map(productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest), ProductList.class);
    }
}
