package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.dtos.ProductDto;
import be.syntra.devshop.DevshopBack.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MapperUtility {

    public Product convertToProduct(ProductDto productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build();
    }

    public ProductDto convertToProductDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public List<ProductDto> convertListToDtos(List<Product> products) {
        return products
                .stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
