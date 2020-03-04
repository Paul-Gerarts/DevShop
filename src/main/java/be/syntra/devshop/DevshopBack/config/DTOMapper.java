package be.syntra.devshop.DevshopBack.config;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.model.ProductDTO;


public class DTOMapper {

    public Product convertToProduct(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build();
    }
}
