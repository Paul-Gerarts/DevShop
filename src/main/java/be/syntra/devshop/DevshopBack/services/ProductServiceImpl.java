package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.ProductDTO;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.mappers.DTOToEntityMapperService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private DTOToEntityMapperService dtoToEntityMapperService;

    public ProductServiceImpl(ProductRepository productRepository, DTOToEntityMapperService dtoToEntityMapperService) {
        this.productRepository = productRepository;
        this.dtoToEntityMapperService = dtoToEntityMapperService;
    }


    @Override
    public void productToDataBase(ProductDTO productDTO) {
        productRepository.save(dtoToEntityMapperService.convertToProduct(productDTO));
    }
}
