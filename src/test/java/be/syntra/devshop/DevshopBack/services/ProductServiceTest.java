package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.utilities.MapperUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createProductDto;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createProductList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private MapperUtility mapperUtility;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllProductsTest() {
        // given
        List<Product> dummyProducts = createProductList();
        when(productRepository.findAll()).thenReturn(dummyProducts);

        // when
        List<Product> resultProductList = productService.findAll();

        // then
        assertEquals(resultProductList,dummyProducts);
        verify(productRepository,times(1)).findAll();
    }

    @Test
    void saveProductTest(){
        // given
        ProductDto dummyDto = createProductDto();

        // when
        ProductDto resultProductDto = productService.save(dummyDto);

        // then
        assertEquals(dummyDto,resultProductDto);
        verify(productRepository,times(1)).save(any());
    }
}
