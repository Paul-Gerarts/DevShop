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
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategory_Headphones;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.*;
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

    @Test
    void saveProductTest() {
        // given
        final Product product = createNonArchivedProduct();

        // when
        final Product resultProduct = productService.save(product);

        // then
        assertEquals(product, resultProduct);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void canGetProductByIdTest() {
        // given
        final Product dummyProduct = createNonArchivedProduct();
        when(productRepository.findById(dummyProduct.getId())).thenReturn(Optional.of(dummyProduct));

        // when
        final Product resultProduct = productService.findById(dummyProduct.getId());

        // then
        assertThat(resultProduct).isEqualTo(dummyProduct);
        verify(productRepository, times(1)).findById(dummyProduct.getId());
    }

    @Test
    void canGetNonArchivedProductTest() {
        // given
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        when(productRepository.findAllByArchivedFalse(dummyPageable)).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultProductList = productService.findAllByArchivedFalse(dummyPageable);

        // then
        assertEquals(resultProductList, dummyProductPage);
        verify(productRepository, times(1)).findAllByArchivedFalse(dummyPageable);
    }

    @Test
    void canFindMaxPriceProductByArchiveFalseTest() {
        // given
        final Page<Product> dummyProductPage = createDummyProductPage();
        when(productRepository.findAllByArchivedFalse(PageRequest.of(0, 1, Sort.by("price").descending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> maxPriceProductByArchivedFalse = productService.findMaxPriceProductByArchivedFalse();

        // then
        assertEquals(maxPriceProductByArchivedFalse, dummyProductPage);
        verify(productRepository, times(1)).findAllByArchivedFalse(PageRequest.of(0, 1, Sort.by("price").descending()));
    }

    @Test
    void canFindMinPriceProductByArchiveFalseTest() {
        // given
        final Page<Product> dummyProductPage = createDummyProductPage();
        when(productRepository.findAllByArchivedFalse(PageRequest.of(0, 1, Sort.by("price").ascending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMinPriceProductByArchivedFalse();

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByArchivedFalse(PageRequest.of(0, 1, Sort.by("price").ascending()));
    }

    @Test
    void canFindAllByNameContainingIgnoreCaseAndArchivedFalseTest() {
        // given
        final String searchRequest = "POst";
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        when(productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, dummyPageable)).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultProductPage = productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, dummyPageable);

        // then
        assertEquals(resultProductPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, dummyPageable);
    }

    @Test
    void canFindMaxPriceProductByNameContainingIgnoreCaseAndArchivedFalseTest() {
        // given
        final String searchRequest = "POst";
        final Page<Product> dummyProductPage = createDummyProductPage();
        when(productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, PageRequest.of(0, 1, Sort.by("price").descending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> result = productService.findMaxPriceProductByNameContainingIgnoreCaseAndArchivedFalse(searchRequest);

        // then
        assertEquals(result, dummyProductPage);
        verify(productRepository, times(1)).findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, PageRequest.of(0, 1, Sort.by("price").descending()));
    }

    @Test
    void canFindMinPriceProductByNameContainingIgnoreCaseAndArchivedFalseTest() {
        // given
        final String searchRequest = "POst";
        final Page<Product> dummyProductPage = createDummyProductPage();
        when(productRepository.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, PageRequest.of(0, 1, Sort.by("price").ascending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> result = productService.findMinPriceProductByNameContainingIgnoreCaseAndArchivedFalse(searchRequest);

        // then
        assertEquals(result, dummyProductPage);
        verify(productRepository, times(1)).findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest, PageRequest.of(0, 1, Sort.by("price").ascending()));
    }

    @Test
    void canGetArchivedProductTest() {
        // given
        final Pageable dummyPageable = createDummyPageable();
        final Page<Product> dummyProductPage = createDummyProductPage();
        when(productRepository.findAllByArchivedTrue(dummyPageable)).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findAllByArchivedTrue(dummyPageable);

        // then
        assertThat(resultPage).isEqualTo(dummyProductPage);
        verify(productRepository, times(1)).findAllByArchivedTrue(dummyPageable);
    }

    @Test
    void canFindMaxPriceProductByArchivedTrueTest() {
        // given
        final Page<Product> dummyProductPage = createDummyProductPage();
        when(productRepository.findAllByArchivedTrue(PageRequest.of(0, 1, Sort.by("price").descending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMaxPriceProductByArchivedTrue();

        // then
        assertThat(resultPage).isEqualTo(dummyProductPage);
        verify(productRepository, times(1)).findAllByArchivedTrue(PageRequest.of(0, 1, Sort.by("price").descending()));
    }

    @Test
    void canFindMinPriceProductByArchivedTrueTest() {
        // given
        final Page<Product> dummyProductPage = createDummyProductPage();
        when(productRepository.findAllByArchivedTrue(PageRequest.of(0, 1, Sort.by("price").ascending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMinPriceProductByArchivedTrue();

        // then
        assertThat(resultPage).isEqualTo(dummyProductPage);
        verify(productRepository, times(1)).findAllByArchivedTrue(PageRequest.of(0, 1, Sort.by("price").ascending()));
    }

    @Test
    void canFindAllByDescriptionAndByArchivedFalseTest() {
        // given
        final Pageable dummyPageable = createDummyPageable();
        final Page<Product> dummyProductPage = createDummyProductPage();
        final String descriptionString = "description";
        when(productRepository.findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(descriptionString, dummyPageable)).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productRepository.findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(descriptionString, dummyPageable);

        //then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(descriptionString, dummyPageable);
    }

    @Test
    void canFindMaxPriceProductByDescriptionAndByArchivedFalseTest() {
        // given
        final String description = "description";
        final Page<Product> dummyProductPage = createDummyProductPage();
        when(productRepository.findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(description, PageRequest.of(0, 1, Sort.by("price").descending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMaxPriceProductByDescriptionAndByArchivedFalse(description);

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(description, PageRequest.of(0, 1, Sort.by("price").descending()));
    }

    @Test
    void canFindMinPriceProductByDescriptionAndByArchivedFalseTest() {
        // given
        final String description = "description";
        final Page<Product> dummyProductPage = createDummyProductPage();
        when(productRepository.findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(description, PageRequest.of(0, 1, Sort.by("price").ascending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMinPriceProductByDescriptionAndByArchivedFalse(description);

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByDescriptionContainingIgnoreCaseAndArchivedFalse(description, PageRequest.of(0, 1, Sort.by("price").ascending()));
    }

    @Test
    void canFindAllByPriceBetweenTest() {
        // given
        final Pageable dummyPageable = createDummyPageable();
        final Page<Product> dummyProductPage = createDummyProductPage();
        final BigDecimal priceMin = BigDecimal.ZERO;
        final BigDecimal priceMax = BigDecimal.TEN;
        when(productRepository.findAllByPriceIsBetween(priceMin, priceMax, dummyPageable)).thenReturn(dummyProductPage);

        // when
        Page<Product> resultPage = productRepository.findAllByPriceIsBetween(priceMin, priceMax, dummyPageable);

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByPriceIsBetween(priceMin, priceMax, dummyPageable);
    }

    @Test
    void canFindAllNonArchivedBySearchTermAndPriceBetweenTest() {
        // given
        final String searchRequest = "searchRequest";
        final Pageable dummyPageable = createDummyPageable();
        final Page<Product> dummyProductPage = createDummyProductPage();
        final BigDecimal priceMin = BigDecimal.ZERO;
        final BigDecimal priceMax = BigDecimal.TEN;
        when(productRepository.findAllByNameContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(searchRequest, priceMin, priceMax, dummyPageable)).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findAllNonArchivedBySearchTermAndPriceBetween(searchRequest, priceMin, priceMax, dummyPageable);

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByNameContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(searchRequest, priceMin, priceMax, dummyPageable);
    }

    @Test
    void canFindMaxPriceProductNonArchivedBySearchTermAndPriceBetweenTest() {
        // given
        final String searchRequest = "searchRequest";
        final Page<Product> dummyProductPage = createDummyProductPage();
        final BigDecimal priceMin = BigDecimal.ZERO;
        final BigDecimal priceMax = BigDecimal.TEN;
        when(productRepository.findAllByNameContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(searchRequest, priceMin, priceMax, PageRequest.of(0, 1, Sort.by("price").descending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMaxPriceProductNonArchivedBySearchTermAndPriceBetween(searchRequest, priceMin, priceMax);

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByNameContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(searchRequest, priceMin, priceMax, PageRequest.of(0, 1, Sort.by("price").descending()));
    }

    @Test
    void canFindMinPriceProductNonArchivedBySearchTermAndPriceBetweenTest() {
        // given
        final String searchRequest = "searchRequest";
        final Page<Product> dummyProductPage = createDummyProductPage();
        final BigDecimal priceMin = BigDecimal.ZERO;
        final BigDecimal priceMax = BigDecimal.TEN;
        when(productRepository.findAllByNameContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(searchRequest, priceMin, priceMax, PageRequest.of(0, 1, Sort.by("price").ascending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMinPriceProductNonArchivedBySearchTermAndPriceBetween(searchRequest, priceMin, priceMax);

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByNameContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(searchRequest, priceMin, priceMax, PageRequest.of(0, 1, Sort.by("price").ascending()));
    }

    @Test
    void canFindAllNonArchivedByDescriptionAndPriceBetweenTest() {
        // given
        final String description = "description";
        final Pageable dummyPageable = createDummyPageable();
        final Page<Product> dummyProductPage = createDummyProductPage();
        final BigDecimal priceLow = BigDecimal.ZERO;
        final BigDecimal priceHigh = BigDecimal.TEN;
        when(productRepository.findAllByDescriptionContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(description, priceLow, priceHigh, dummyPageable)).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findAllNonArchivedByDescriptionAndPriceBetween(description, priceLow, priceHigh, dummyPageable);

        //then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByDescriptionContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(description, priceLow, priceHigh, dummyPageable);
    }

    @Test
    void canFindMaxPriceProductNonArchivedByDescriptionAndPriceBetweenTest() {
        // given
        final String description = "description";
        final Page<Product> dummyProductPage = createDummyProductPage();
        final BigDecimal priceLow = BigDecimal.ZERO;
        final BigDecimal priceHigh = BigDecimal.TEN;
        when(productRepository.findAllByDescriptionContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(description, priceLow, priceHigh, PageRequest.of(0, 1, Sort.by("price").descending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMaxPriceProductNonArchivedByDescriptionAndPriceBetween(description, priceLow, priceHigh);

        //then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByDescriptionContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(description, priceLow, priceHigh, PageRequest.of(0, 1, Sort.by("price").descending()));
    }

    @Test
    void canFindMinPriceProductNonArchivedByDescriptionAndPriceBetweenTest() {
        // given
        final String description = "description";
        final Page<Product> dummyProductPage = createDummyProductPage();
        final BigDecimal priceLow = BigDecimal.ZERO;
        final BigDecimal priceHigh = BigDecimal.TEN;
        when(productRepository.findAllByDescriptionContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(description, priceLow, priceHigh, PageRequest.of(0, 1, Sort.by("price").ascending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMinPriceProductNonArchivedByDescriptionAndPriceBetween(description, priceLow, priceHigh);

        //then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByDescriptionContainingIgnoreCaseAndPriceIsBetweenAndArchivedIsFalse(description, priceLow, priceHigh, PageRequest.of(0, 1, Sort.by("price").ascending()));
    }

    @Test
    void canFindAllArchivedFalseByPriceBetweenTest() {
        // given
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        final BigDecimal priceLow = BigDecimal.ZERO;
        final BigDecimal priceHigh = BigDecimal.TEN;
        when(productRepository.findAllByPriceIsBetweenAndArchivedFalse(priceLow, priceHigh, dummyPageable)).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findAllArchivedFalseByPriceBetween(priceLow, priceHigh, dummyPageable);

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByPriceIsBetweenAndArchivedFalse(priceLow, priceHigh, dummyPageable);
    }

    @Test
    void canFindMaxPriceProductArchivedFalseByPriceBetweenTest() {
        // given
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        final BigDecimal priceLow = BigDecimal.ZERO;
        final BigDecimal priceHigh = BigDecimal.TEN;
        when(productRepository.findAllByPriceIsBetweenAndArchivedFalse(priceLow, priceHigh, PageRequest.of(0, 1, Sort.by("price").descending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMaxPriceProductArchivedFalseByPriceBetween(priceLow, priceHigh);

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByPriceIsBetweenAndArchivedFalse(priceLow, priceHigh, PageRequest.of(0, 1, Sort.by("price").descending()));
    }

    @Test
    void canFindMinPriceProductArchivedFalseByPriceBetweenTest() {
        // given
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        final BigDecimal priceLow = BigDecimal.ZERO;
        final BigDecimal priceHigh = BigDecimal.TEN;
        when(productRepository.findAllByPriceIsBetweenAndArchivedFalse(priceLow, priceHigh, PageRequest.of(0, 1, Sort.by("price").ascending()))).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findMinPriceProductArchivedFalseByPriceBetween(priceLow, priceHigh);

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllByPriceIsBetweenAndArchivedFalse(priceLow, priceHigh, PageRequest.of(0, 1, Sort.by("price").ascending()));
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
    void canGetAllProductsWithCorrespondingCategoryTest() {
        // given
        Category category = createCategory_Headphones();
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
        Category category = createCategory_Headphones();
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

    @Test
    void canRemoveOneCategoryTest() {
        // given
        Category category = createCategory_Headphones();
        CategoryChangeDto categoryChangeDto = CategoryChangeDto.builder()
                .categoryToDelete(1L)
                .build();
        List<Product> dummyProducts = createDummyNonArchivedProductList();
        when(categoryService.findById(categoryChangeDto.getCategoryToDelete())).thenReturn(category);
        when(productRepository.findAllWithCorrespondingCategories(category.getId())).thenReturn(dummyProducts);

        // when
        productService.removeOneCategory(categoryChangeDto.getCategoryToDelete());

        // then
        assertThat(dummyProducts.get(0).getCategories().get(0).getName()).isNotEqualTo(category.getName());
        verify(productRepository, times(1)).findAllWithCorrespondingCategories(category.getId());
        verify(productRepository, times(1)).saveAll(any());
        verify(categoryService, times(1)).findById(categoryChangeDto.getCategoryToDelete());
    }
}
