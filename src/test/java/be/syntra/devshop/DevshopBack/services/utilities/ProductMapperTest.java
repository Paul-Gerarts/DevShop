package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.models.ProductPage;
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

    @Test
    void convertToProductListTest() {
        // given
        List<ProductDto> productDtoList = createDummyProductDtoList();

        // when
        List<Product> resultList = productMapper.convertToProductList(productDtoList);

        // then
        assertEquals(resultList.get(0).getClass(), Product.class);
        assertEquals(resultList.get(0).getName(), productDtoList.get(0).getName());
        assertEquals(resultList.get(1).getName(), productDtoList.get(1).getName());
        assertEquals(resultList.get(0).getPrice(), productDtoList.get(0).getPrice());
        assertEquals(resultList.get(1).getPrice(), productDtoList.get(1).getPrice());
    }

    @Test
    void convertProductPageToProductListObjectTest(){
        // given
        ProductPage productPage = createProductPage();

        // when
        ProductList resultProductListObject = productMapper.convertToProductListObject(productPage);

        // then
        assertEquals(resultProductListObject.getClass(),ProductList.class);
        assertEquals(resultProductListObject.getProducts(), productPage.getProductPage().getContent());
    }

     @Test
    void convertListToProductListObjectTest(){
        // given
        List<Product> productList = createProductList();

        // when
        ProductList resultProductListObject = productMapper.convertToProductListObject(productList);

        // then
        assertEquals(resultProductListObject.getClass(),ProductList.class);
        assertEquals(resultProductListObject.getProducts(),productList);
    }



}