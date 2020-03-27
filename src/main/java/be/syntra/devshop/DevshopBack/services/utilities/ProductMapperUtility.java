package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductMapperUtility {

    public static Product convertToProduct(ProductDto productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build();
    }

    public static ProductDto convertToProductDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    static List<Product> convertToProductList(List<ProductDto> productDtoList) {
        return productDtoList.stream()
                .map(ProductMapperUtility::convertToProduct)
                .collect(Collectors.toUnmodifiableList());
    }

    static List<ProductDto> convertToProductDtoList(List<Product> products) {
        return products.stream()
                .map(ProductMapperUtility::convertToProductDto)
                .collect(Collectors.toUnmodifiableList());
    }

}
