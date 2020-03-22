package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Cart;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.factories.ProductFactory;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductFactory productFactory;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductDto save(ProductDto productDTO) {
        productRepository.save(ProductMapperUtility.convertToProduct(productDTO));
        return productDTO;
    }

    public void initialize() {
        if (productRepository.count() == 0) {
            productRepository.saveAll(List.of(
                    productFactory.createProduct(
                            "keyboard",
                            new BigDecimal(150),
                            new Cart()),
                    productFactory.createProduct(
                            "mousepad",
                            new BigDecimal(3),
                            new Cart())
                    )
            );
        }
    }
}
