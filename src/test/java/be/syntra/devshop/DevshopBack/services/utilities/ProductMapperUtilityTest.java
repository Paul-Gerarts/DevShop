package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.testutilities.ProductUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperUtilityTest {

    private ProductMapperUtility mapperUtility = new ProductMapperUtility();

    @Test
    void convertToProductTest() {
        // given
        ProductDto productDto = ProductUtils.createProductDto();

        // when
        Product mappedProduct = ProductMapperUtility.convertToProduct(productDto);

        // then
        assertEquals(mappedProduct.getClass(), Product.class);
        assertEquals(mappedProduct.getName(), productDto.getName());
        assertEquals(mappedProduct.getPrice(), productDto.getPrice());
    }

    @Test
    void convertToProductDtoTest() {
        // given
        Product product = ProductUtils.createProductWithId();

        // when
        ProductDto mappedProductDto = ProductMapperUtility.convertToProductDto(product);

        // then
        assertEquals(mappedProductDto.getClass(), ProductDto.class);
        assertEquals(mappedProductDto.getName(), product.getName());
        assertEquals(mappedProductDto.getPrice(), product.getPrice());
    }

    @Test
    void convertListToDtoListTest() {
        // given
        List<Product> dummyProductList = ProductUtils.createDummyNonArchivedProductList();

        // when
        List<ProductDto> mappedToProductDtoList = ProductMapperUtility.convertToProductDtoList(dummyProductList);

        // then
        assertEquals(mappedToProductDtoList.get(0).getClass(), ProductDto.class);
        assertEquals(dummyProductList.size(), mappedToProductDtoList.size());
        assertEquals(mappedToProductDtoList.get(0).getName(), dummyProductList.get(0).getName());
        assertEquals(mappedToProductDtoList.get(1).getName(), dummyProductList.get(1).getName());
        assertEquals(mappedToProductDtoList.get(0).getPrice(), dummyProductList.get(0).getPrice());
        assertEquals(mappedToProductDtoList.get(1).getPrice(), dummyProductList.get(1).getPrice());
    }
}