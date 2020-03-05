package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface DTOMapperService {
    Product convertToProduct(ProductDTO productDTO);
}
