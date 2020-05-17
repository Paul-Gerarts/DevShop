package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.SearchModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyPageable;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyProductPage;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SearchServiceTest {

    @Mock
    ProductService productService;

    @InjectMocks
    SearchServiceImpl searchService;

    @Test
    void findAllNonArchivedBySearchTermAndPriceBetween() {
        // given
        SearchModel dummySearchModel = new SearchModel();
        dummySearchModel.setSearchResultView(true);
        dummySearchModel.setSearchRequest("test");
        dummySearchModel.setActiveFilters(true);
        dummySearchModel.setPriceLow(BigDecimal.ZERO);
        dummySearchModel.setPriceHigh(BigDecimal.TEN);
        dummySearchModel.setPageNumber(0);
        dummySearchModel.setPageSize(10);
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        when(productService.findAllNonArchivedBySearchTermAndPriceBetween(dummySearchModel.getSearchRequest(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh(), dummyPageable)).thenReturn(dummyProductPage);
        when(productService.findMinPriceProductNonArchivedBySearchTermAndPriceBetween(dummySearchModel.getSearchRequest(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh())).thenReturn(dummyProductPage);
        when(productService.findMaxPriceProductNonArchivedBySearchTermAndPriceBetween(dummySearchModel.getSearchRequest(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh())).thenReturn(dummyProductPage);

        // when
        searchService.applySearchModel(dummySearchModel);

        // then
        verify(productService, times(1)).findAllNonArchivedBySearchTermAndPriceBetween(dummySearchModel.getSearchRequest(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh(), dummyPageable);
        verify(productService, times(1)).findMinPriceProductNonArchivedBySearchTermAndPriceBetween(dummySearchModel.getSearchRequest(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh());
        verify(productService, times(1)).findMaxPriceProductNonArchivedBySearchTermAndPriceBetween(dummySearchModel.getSearchRequest(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh());
    }

    @Test
    void findAllByNameContainingIgnoreCaseAndArchivedFalse() {
        // given
        SearchModel dummySearchModel = new SearchModel();
        dummySearchModel.setSearchResultView(true);
        dummySearchModel.setSearchRequest("test");
        dummySearchModel.setActiveFilters(false);
        dummySearchModel.setPriceLow(BigDecimal.ZERO);
        dummySearchModel.setPriceHigh(BigDecimal.TEN);
        dummySearchModel.setPageNumber(0);
        dummySearchModel.setPageSize(10);
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        when(productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(dummySearchModel.getSearchRequest(), dummyPageable)).thenReturn(dummyProductPage);
        when(productService.findMinPriceProductByNameContainingIgnoreCaseAndArchivedFalse(dummySearchModel.getSearchRequest())).thenReturn(dummyProductPage);
        when(productService.findMaxPriceProductByNameContainingIgnoreCaseAndArchivedFalse(dummySearchModel.getSearchRequest())).thenReturn(dummyProductPage);

        // when
        searchService.applySearchModel(dummySearchModel);

        // then
        verify(productService, times(1)).findAllByNameContainingIgnoreCaseAndArchivedFalse(dummySearchModel.getSearchRequest(), dummyPageable);
        verify(productService, times(1)).findMinPriceProductByNameContainingIgnoreCaseAndArchivedFalse(dummySearchModel.getSearchRequest());
        verify(productService, times(1)).findMaxPriceProductByNameContainingIgnoreCaseAndArchivedFalse(dummySearchModel.getSearchRequest());
    }

    @Test
    void findAllNonArchivedByDescriptionAndPriceBetween() {
        // given
        SearchModel dummySearchModel = new SearchModel();
        dummySearchModel.setSearchResultView(true);
        dummySearchModel.setSearchRequest("");
        dummySearchModel.setDescription("test");
        dummySearchModel.setActiveFilters(true);
        dummySearchModel.setPriceLow(BigDecimal.ZERO);
        dummySearchModel.setPriceHigh(BigDecimal.TEN);
        dummySearchModel.setPageNumber(0);
        dummySearchModel.setPageSize(10);
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        when(productService.findAllNonArchivedByDescriptionAndPriceBetween(dummySearchModel.getDescription(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh(), dummyPageable)).thenReturn(dummyProductPage);
        when(productService.findMinPriceProductNonArchivedByDescriptionAndPriceBetween(dummySearchModel.getDescription(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh())).thenReturn(dummyProductPage);
        when(productService.findMaxPriceProductNonArchivedByDescriptionAndPriceBetween(dummySearchModel.getDescription(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh())).thenReturn(dummyProductPage);

        // when
        searchService.applySearchModel(dummySearchModel);

        // then
        verify(productService, times(1)).findAllNonArchivedByDescriptionAndPriceBetween(dummySearchModel.getDescription(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh(), dummyPageable);
        verify(productService, times(1)).findMinPriceProductNonArchivedByDescriptionAndPriceBetween(dummySearchModel.getDescription(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh());
        verify(productService, times(1)).findMaxPriceProductNonArchivedByDescriptionAndPriceBetween(dummySearchModel.getDescription(), dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh());
    }

    @Test
    void findAllByDescriptionAndByArchivedFalse() {
        // given
        SearchModel dummySearchModel = new SearchModel();
        dummySearchModel.setSearchResultView(true);
        dummySearchModel.setSearchRequest("");
        dummySearchModel.setDescription("test");
        dummySearchModel.setActiveFilters(false);
        dummySearchModel.setPriceLow(BigDecimal.ZERO);
        dummySearchModel.setPriceHigh(BigDecimal.TEN);
        dummySearchModel.setPageNumber(0);
        dummySearchModel.setPageSize(10);
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        when(productService.findAllByDescriptionAndByArchivedFalse(dummySearchModel.getDescription(), dummyPageable)).thenReturn(dummyProductPage);
        when(productService.findMinPriceProductByDescriptionAndByArchivedFalse(dummySearchModel.getDescription())).thenReturn(dummyProductPage);
        when(productService.findMaxPriceProductByDescriptionAndByArchivedFalse(dummySearchModel.getDescription())).thenReturn(dummyProductPage);

        // when
        searchService.applySearchModel(dummySearchModel);

        // then
        verify(productService, times(1)).findAllByDescriptionAndByArchivedFalse(dummySearchModel.getDescription(), dummyPageable);
        verify(productService, times(1)).findMinPriceProductByDescriptionAndByArchivedFalse(dummySearchModel.getDescription());
        verify(productService, times(1)).findMaxPriceProductByDescriptionAndByArchivedFalse(dummySearchModel.getDescription());
    }

    @Test
    void findAllArchivedFalseByPriceBetween() {
        // given
        SearchModel dummySearchModel = new SearchModel();
        dummySearchModel.setSearchResultView(true);
        dummySearchModel.setSearchRequest("");
        dummySearchModel.setDescription("");
        dummySearchModel.setActiveFilters(true);
        dummySearchModel.setPriceLow(BigDecimal.ZERO);
        dummySearchModel.setPriceHigh(BigDecimal.TEN);
        dummySearchModel.setPageNumber(0);
        dummySearchModel.setPageSize(10);
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        when(productService.findAllArchivedFalseByPriceBetween(dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh(), dummyPageable)).thenReturn(dummyProductPage);
        when(productService.findMinPriceProductArchivedFalseByPriceBetween(dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh())).thenReturn(dummyProductPage);
        when(productService.findMaxPriceProductArchivedFalseByPriceBetween(dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh())).thenReturn(dummyProductPage);

        // when
        searchService.applySearchModel(dummySearchModel);

        // then
        verify(productService, times(1)).findAllArchivedFalseByPriceBetween(dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh(), dummyPageable);
        verify(productService, times(1)).findMinPriceProductArchivedFalseByPriceBetween(dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh());
        verify(productService, times(1)).findMaxPriceProductArchivedFalseByPriceBetween(dummySearchModel.getPriceLow(), dummySearchModel.getPriceHigh());
    }

    @Test
    void findAllByArchivedTrue() {
        // given
        SearchModel dummySearchModel = new SearchModel();
        dummySearchModel.setArchivedView(true);
        dummySearchModel.setSearchRequest("");
        dummySearchModel.setDescription("");
        dummySearchModel.setActiveFilters(true);
        dummySearchModel.setPriceLow(BigDecimal.ZERO);
        dummySearchModel.setPriceHigh(BigDecimal.TEN);
        dummySearchModel.setPageNumber(0);
        dummySearchModel.setPageSize(10);
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        when(productService.findAllByArchivedTrue(dummyPageable)).thenReturn(dummyProductPage);
        when(productService.findMinPriceProductByArchivedTrue()).thenReturn(dummyProductPage);
        when(productService.findMaxPriceProductByArchivedTrue()).thenReturn(dummyProductPage);

        // when
        searchService.applySearchModel(dummySearchModel);

        // then
        verify(productService, times(1)).findAllByArchivedTrue(dummyPageable);
        verify(productService, times(1)).findMinPriceProductByArchivedTrue();
        verify(productService, times(1)).findMaxPriceProductByArchivedTrue();
    }

    @Test
    void findAllByArchivedFalse() {
        // given
        SearchModel dummySearchModel = new SearchModel();
        dummySearchModel.setArchivedView(false);
        dummySearchModel.setSearchRequest("");
        dummySearchModel.setDescription("");
        dummySearchModel.setActiveFilters(true);
        dummySearchModel.setPriceLow(BigDecimal.ZERO);
        dummySearchModel.setPriceHigh(BigDecimal.TEN);
        dummySearchModel.setPageNumber(0);
        dummySearchModel.setPageSize(10);
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        when(productService.findAllByArchivedFalse(dummyPageable)).thenReturn(dummyProductPage);
        when(productService.findMinPriceProductByArchivedFalse()).thenReturn(dummyProductPage);
        when(productService.findMaxPriceProductByArchivedFalse()).thenReturn(dummyProductPage);

        // when
        searchService.applySearchModel(dummySearchModel);

        // then
        verify(productService, times(1)).findAllByArchivedFalse(dummyPageable);
        verify(productService, times(1)).findMinPriceProductByArchivedFalse();
        verify(productService, times(1)).findMaxPriceProductByArchivedFalse();
    }

}