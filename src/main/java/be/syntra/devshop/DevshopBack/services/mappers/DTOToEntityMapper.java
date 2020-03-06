package be.syntra.devshop.DevshopBack.services.mappers;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDTO;
import org.springframework.stereotype.Service;


@Service
public class DTOToEntityMapper implements DTOToEntityMapperService {
    public Product convertToProduct(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build();
    }
}
