package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.testutilities.ProductUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperUtilityTest {

    MapperUtility mapperUtility = new MapperUtility();

    @Test
    void convertToProduct() {
        // given
        Product product = ProductUtils.createProduct();
        ProductDto productDto = ProductUtils.createProductDto();

        // when
        Product mappedProduct = mapperUtility.convertToProduct(productDto);

        // then
        assertEquals(mappedProduct.getClass(),Product.class);
        assertEquals(mappedProduct.getName(),product.getName());
        assertEquals(mappedProduct.getPrice(),product.getPrice());
    }

    @Test
    void convertToProductDto() {
        // given
        Product product = ProductUtils.createProductWithId();
        ProductDto productDto = ProductUtils.createProductDto();

        // when
        ProductDto mappedProductDto = mapperUtility.convertToProductDto(product);

        // then
        assertEquals(mappedProductDto.getClass(),ProductDto.class);
        assertEquals(mappedProductDto.getName(),productDto.getName());
        assertEquals(mappedProductDto.getPrice(),productDto.getPrice());
    }

    @Test
    void convertListToDtos() {
        // given
        List<Product> dummyProductList = ProductUtils.createDummyProductList();

        // when
        List<ProductDto> mappedToProductDtoList = mapperUtility.convertListToDtos(dummyProductList);

        // then
        assertEquals(mappedToProductDtoList.get(0).getClass(),ProductDto.class);
        assertEquals(dummyProductList.size(),mappedToProductDtoList.size());
        assertEquals(mappedToProductDtoList.get(0).getName(),dummyProductList.get(0).getName());
        assertEquals(mappedToProductDtoList.get(1).getName(),dummyProductList.get(1).getName());
        assertEquals(mappedToProductDtoList.get(0).getPrice(),dummyProductList.get(0).getPrice());
        assertEquals(mappedToProductDtoList.get(1).getPrice(),dummyProductList.get(1).getPrice());
    }
}