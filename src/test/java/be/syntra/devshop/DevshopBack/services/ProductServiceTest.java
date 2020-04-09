package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductMapperUtility mapperUtility;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DozerBeanMapper dozerMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void init() {
        dozerMapper = new DozerBeanMapper();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllProductsTest() {
        // given
        List<Product> dummyProducts = createProductList();
        when(productRepository.findAll()).thenReturn(dummyProducts);
        when(dozerMapper.map(dummyProducts, ProductList.class)).thenReturn(new ProductList(dummyProducts));

        // when
        List<Product> resultProductList = productService.findAll().getProducts();

        // then
        assertEquals(resultProductList, dummyProducts);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void saveProductTest() {
        // given
        ProductDto dummyDto = createProductDto();

        // when
        ProductDto resultProductDto = productService.save(dummyDto);

        // then
        assertEquals(dummyDto, resultProductDto);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void canGetProductByIdTest() {
        // given
        Product dummyProduct = createNonArchivedProduct();
        when(productRepository.findById(dummyProduct.getId())).thenReturn(Optional.of(dummyProduct));

        // when
        Product resultProduct = productService.findById(dummyProduct.getId());

        // then
        assertThat(resultProduct).isEqualTo(dummyProduct);
        verify(productRepository, times(1)).findById(dummyProduct.getId());
    }

    @Test
    void cannotGetArchivedProductTest() {
        // given
        Product dummyActiveProduct = createNonArchivedProduct();
        List<Product> dummyProductList = List.of(dummyActiveProduct);
        when(productRepository.findAllByArchivedFalse()).thenReturn(dummyProductList);
        when(dozerMapper.map(dummyProductList, ProductList.class)).thenReturn(new ProductList(dummyProductList));

        // when
        List<Product> resultProductList = productService.findAllByArchivedFalse().getProducts();

        // then
        assertThat(resultProductList).isEqualTo(dummyProductList);
        verify(productRepository, times(1)).findAllByArchivedFalse();
    }

    @Test
    void cannotGetNonArchivedProductTest() {
        // given
        Product dummyArchivedProduct = createArchivedProduct();
        List<Product> dummyProductList = List.of(dummyArchivedProduct);
        when(productRepository.findAllByArchivedTrue()).thenReturn(dummyProductList);
        when(dozerMapper.map(dummyProductList, ProductList.class)).thenReturn(new ProductList(dummyProductList));

        // when
        List<Product> resultProductList = productService.findAllByArchivedTrue().getProducts();

        // then
        assertThat(resultProductList).isEqualTo(dummyProductList);
        verify(productRepository, times(1)).findAllByArchivedTrue();
    }

    @Test
    void exceptionIsThrownWhenProductNotFoundTest() {
        // given
        String errorMessage = "product cannot be found!";
        when(productRepository.findById(1L)).thenThrow(new ProductNotFoundException(errorMessage));

        // when - then
        assertThrows(ProductNotFoundException.class, () -> productRepository.findById(1L));
    }

    @Test
    void canGetProductBySearchRequestTest() {
        // given
        String searchRequest = "POst";
        List<Product> dummyProductList = List.of(createNonArchivedProduct());
        when(productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest)).thenReturn(dummyProductList);
        when(dozerMapper.map(dummyProductList, ProductList.class)).thenReturn(new ProductList(dummyProductList));

        // when
        List<Product> resultProduct = productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest).getProducts();

        // then
        assertThat(resultProduct).isEqualTo(dummyProductList);
        verify(productRepository, times(1)).findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest);
    }
}
