package be.syntra.devshop.DevshopBack.services.mappers;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface DTOToEntityMapperService {
    Product convertToProduct(ProductDTO productDTO);
}
