package be.syntra.devshop.DevshopBack.services.mappers;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDTO;


public interface DTOToEntityMapperService {
    Product convertToProduct(ProductDTO productDTO);
}
