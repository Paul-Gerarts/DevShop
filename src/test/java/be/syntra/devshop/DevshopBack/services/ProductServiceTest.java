package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.models.CategoryChangeDto;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategory;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createArchivedProduct;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createNonArchivedProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

/*    @Test
    void getAllProductsTest() {
        // given
        List<Product> dummyProducts = createProductList();
        when(productRepository.findAll()).thenReturn(dummyProducts);

        // when
        List<Product> resultProductList = productService.findAll();

        // then
        assertEquals(resultProductList, dummyProducts);
        verify(productRepository, times(1)).findAll();
    }*/

    @Test
    void saveProductTest() {
        // given
        Product product = createNonArchivedProduct();

        // when
        Product resultProduct = productService.save(product);

        // then
        assertEquals(product, resultProduct);
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

/*    @Test
    void canGetArchivedProductTest() {
        // given
        Product dummyActiveProduct = createArchivedProduct();
        List<Product> dummyProductList = List.of(dummyActiveProduct);
        when(productRepository.findAllByArchivedTrue()).thenReturn(dummyProductList);

        // when
        List<Product> resultProductList = productService.findAllByArchivedTrue();

        // then
        assertThat(resultProductList).isEqualTo(dummyProductList);
        verify(productRepository, times(1)).findAllByArchivedTrue();
    }*/

    @Test
    void canGetNonArchivedProductTest() {
        // given
        Product dummyArchivedProduct = createNonArchivedProduct();
        List<Product> dummyProductList = List.of(dummyArchivedProduct);
        //when(productRepository.findAllByArchivedFalse()).thenReturn(dummyProductList);
//        when(productMapper.convertToProductListObject(dummyProductList)).thenReturn(new ProductList(dummyProductList));
        Pageable p = PageRequest.of(5,2);
        when(productRepository.findAllByArchivedFalse(p)).thenReturn(Page.empty());

        // when
        Page<Product> resultProductList = productService.findAllByArchivedFalse(p);

        // then
        assertThat(resultProductList).isEqualTo(dummyProductList);
        verify(productRepository, times(1)).findAllByArchivedFalse(p);
    }

    @Test
    void exceptionIsThrownWhenProductNotFoundTest() {
        // given
        String errorMessage = "product cannot be found!";
        when(productRepository.findById(1L)).thenThrow(new ProductNotFoundException(errorMessage));

        // when - then
        assertThrows(ProductNotFoundException.class, () -> productRepository.findById(1L));
    }

  /*  @Test
    void canGetProductBySearchRequestTest() {
        // given
        String searchRequest = "POst";
        List<Product> dummyProductList = List.of(createNonArchivedProduct());
        when(productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest)).thenReturn(dummyProductList);

        // when
        List<Product> resultProductList = productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest);

        // then
        assertEquals(resultProductList,dummyProductList);
        verify(productRepository, times(1)).findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest);
    }*/

    @Test
    void canGetAllProductsWithCorrespondingCategoryTest() {
        // given
        Category category = createCategory();
        List<Product> dummyProducts = List.of(createNonArchivedProduct(), createArchivedProduct());
        when(productRepository.findAllWithCorrespondingCategory(category.getId())).thenReturn(dummyProducts);

        // when
        List<Product> result = productService.findAllByCorrespondingCategory(category.getId());

        // then
        assertThat(result).isEqualTo(dummyProducts);
        verify(productRepository, times(1)).findAllWithCorrespondingCategory(category.getId());
    }

    @Test
    void canSetNewCategoryTest() {
        // given
        Category category = createCategory();
        CategoryChangeDto categoryChangeDto = CategoryChangeDto.builder()
                .categoryToDelete(1L)
                .categoryToSet(2L)
                .build();
        List<Product> dummyProducts = List.of(createNonArchivedProduct(), createArchivedProduct());
        when(categoryService.findById(categoryChangeDto.getCategoryToSet())).thenReturn(category);
        when(productRepository.findAllWithCorrespondingCategory(category.getId())).thenReturn(dummyProducts);

        // when
        productService.setNewCategory(categoryChangeDto.getCategoryToDelete(), categoryChangeDto.getCategoryToSet());

        // then
        assertThat(dummyProducts.get(0).getCategories().get(0).getName()).isEqualTo(category.getName());
        verify(productRepository, times(1)).findAllWithCorrespondingCategory(category.getId());
        verify(productRepository, times(1)).saveAll(any());
        verify(categoryService, times(1)).findById(categoryChangeDto.getCategoryToSet());
    }
}
