package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

class ProductMapperTest {

    @InjectMocks
    private ProductMapper productMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    void convertToProductTest() {
        // given
        ProductDto productDto = createProductDto();

        // when
        Product mappedProduct = productMapper.convertToProduct(productDto);

        // then
        assertEquals(mappedProduct.getClass(), Product.class);
        assertEquals(mappedProduct.getName(), productDto.getName());
        assertEquals(mappedProduct.getPrice(), productDto.getPrice());
    }

    @Test
    void convertToProductDtoTest() {
        // given
        Product product = createProductWithId();

        // when
        ProductDto mappedProductDto = productMapper.convertToProductDto(product);

        // then
        assertEquals(mappedProductDto.getClass(), ProductDto.class);
        assertEquals(mappedProductDto.getName(), product.getName());
        assertEquals(mappedProductDto.getPrice(), product.getPrice());
    }

    @Test
    void convertListToDtoListTest() {
        // given
        List<Product> dummyProductList = createDummyNonArchivedProductList();

        // when
        List<ProductDto> mappedToProductDtoList = productMapper.convertToProductDtoList(dummyProductList);

        // then
        assertEquals(mappedToProductDtoList.get(0).getClass(), ProductDto.class);
        assertEquals(dummyProductList.size(), mappedToProductDtoList.size());
        assertEquals(mappedToProductDtoList.get(0).getName(), dummyProductList.get(0).getName());
        assertEquals(mappedToProductDtoList.get(1).getName(), dummyProductList.get(1).getName());
        assertEquals(mappedToProductDtoList.get(0).getPrice(), dummyProductList.get(0).getPrice());
        assertEquals(mappedToProductDtoList.get(1).getPrice(), dummyProductList.get(1).getPrice());
    }
}