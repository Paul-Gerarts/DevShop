package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.utilities.MapperUtility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private MapperUtility mapperUtility;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtility mapperUtility) {
        this.productRepository = productRepository;
        this.mapperUtility = mapperUtility;
    }

    @Override
    public List<ProductDto> findAll() {
        return mapperUtility.convertListToDtos(productRepository.findAll());
    }

    @Override
    public ProductDto save(ProductDto productDTO) {
        productRepository.save(mapperUtility.convertToProduct(productDTO));
        return productDTO;
    }
}
