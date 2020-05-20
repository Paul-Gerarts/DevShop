package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.models.ProductPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Component
public class ProductMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    public Product convertToProduct(ProductDto productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .archived(productDTO.isArchived())
                .description(productDTO.getDescription())
                .categories(categoryMapper.mapToCategory(productDTO.getCategoryNames()))
                .build();
    }

    public ProductDto convertToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .archived(product.isArchived())
                .build();
    }

    List<Product> convertToProductList(List<ProductDto> productDtoList) {
        return productDtoList.stream()
                .map(this::convertToProduct)
                .collect(toUnmodifiableList());
    }

    List<ProductDto> convertToProductDtoList(List<Product> products) {
        return products.stream()
                .map(this::convertToProductDto)
                .collect(toUnmodifiableList());
    }

    public ProductList convertToProductListObject(ProductPage productPage){
        return ProductList.builder()
                .products(productPage.getProductPage().getContent())
                .searchResultMinPrice(productPage.getMinPrice())
                .searchResultMaxPrice(productPage.getMaxPrice())
                .searchFailure(productPage.isSearchFailure())
                .build();
    }

    public ProductList convertToProductListObject(List<Product> productList){
        return ProductList.builder()
                .products(productList)
                .build();
    }
}
