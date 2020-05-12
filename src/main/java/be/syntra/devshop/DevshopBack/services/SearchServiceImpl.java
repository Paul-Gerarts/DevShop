package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductPageAndMaxPrice;
import be.syntra.devshop.DevshopBack.models.SearchModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    @Value("${pagination.default.pageSize}")
    private int paginationDefaultPageSize;
    @Value("${pagination.default.pageNumber}")
    private int paginationDefaultPageNumber;
    private final ProductService productService;

    @Autowired
    public SearchServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductPageAndMaxPrice applySearchModel(SearchModel searchModel) {
        log.info("searchModel -> {}", searchModel);
        Pageable pageable = setSorting(setDefaultPaginationValues(searchModel));

        if (searchModel.isSearchResultView()) {
            if (StringUtils.hasText(searchModel.getSearchRequest())) {
                if (priceFiltersActiveAndValid(searchModel)) {
                    return ProductPageAndMaxPrice.builder()
                            .productPage(
                                    productService.findAllNonArchivedBySearchTermAndPriceBetween(
                                            searchModel.getSearchRequest(), searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable))
                            .maxPrice(
                                    getMaxPriceFromSearch(
                                            productService.findMaxPriceProductNonArchivedBySearchTermAndPriceBetween(
                                                    searchModel.getSearchRequest(), searchModel.getPriceLow(), searchModel.getPriceHigh())))
                            .build();
                } else {
                    return ProductPageAndMaxPrice.builder()
                            .productPage(
                                    productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(
                                            searchModel.getSearchRequest(), pageable))
                            .maxPrice(
                                    getMaxPriceFromSearch(
                                            productService.findMaxPriceProductByNameContainingIgnoreCaseAndArchivedFalse(
                                                    searchModel.getSearchRequest())))
                            .build();
                }
            }
            if (StringUtils.hasText(searchModel.getDescription())) {
                if (priceFiltersActiveAndValid(searchModel)) {
                    return ProductPageAndMaxPrice.builder()
                            .productPage(
                                    productService.findAllNonArchivedByDescriptionAndPriceBetween(searchModel.getDescription(), searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable))
                            .maxPrice(
                                    searchModel.getPriceHigh())
                            .build();
                } else {
                    return ProductPageAndMaxPrice.builder()
                            .productPage(
                                    productService.findAllByDescriptionAndByArchivedFalse(searchModel.getDescription(), pageable))
                            .maxPrice(
                                    getMaxPriceFromSearch(
                                            productService.findMaxPriceProductByDescriptionAndByArchivedFalse(searchModel.getDescription())))
                            .build();
                }
            }
            if (priceFiltersActiveAndValid(searchModel)) {
                return ProductPageAndMaxPrice.builder()
                        .productPage(
                                productService.findAllArchivedFalseByPriceBetween(searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable))
                        .maxPrice(
                                searchModel.getPriceHigh())
                        .build();
            }
        }

        if (searchModel.isArchivedView()) {
            return ProductPageAndMaxPrice.builder()
                    .productPage(
                            productService.findAllByArchivedTrue(pageable))
                    .maxPrice(
                            getMaxPriceFromSearch(
                                    productService.findMaxPriceProductByArchivedTrue()))
                    .build();
        }

        return ProductPageAndMaxPrice.builder()
                .productPage(
                        productService.findAllByArchivedFalse(pageable))
                .maxPrice(
                        getMaxPriceFromSearch(
                                productService.findMaxPriceProductByArchivedFalse()))
                .build();
    }

    private BigDecimal getMaxPriceFromSearch(Page<Product> productPage) {
        return productPage.getContent().stream()
                .findFirst()
                .map(Product::getPrice)
                .orElse(BigDecimal.ZERO);
    }

    private boolean priceFiltersActiveAndValid(SearchModel searchModel) {
        return searchModel.isActiveFilters() &&
                null != searchModel.getPriceLow() &&
                null != searchModel.getPriceHigh();
    }

    private SearchModel setDefaultPaginationValues(SearchModel searchModel) {
        if (paginationInfoPresent(searchModel)) {
            return searchModel;
        }
        searchModel.setPageSize(paginationDefaultPageSize);
        searchModel.setPageNumber(paginationDefaultPageNumber);
        return searchModel;
    }

    private boolean paginationInfoPresent(SearchModel searchModel) {
        return (null != searchModel.getPageSize()) &&
                (searchModel.getPageSize() > 1) &&
                (null != searchModel.getPageNumber());
    }

    private Pageable setSorting(SearchModel searchModel) {
        Sort sort = null;
        if (searchModel.isPriceSortActive()) {
            sort = (searchModel.isSortAscendingPrice()) ?
                    Sort.by("price").ascending() :
                    Sort.by("price").descending();
        }
        if (searchModel.isNameSortActive()) {
            sort = (searchModel.isSortAscendingName()) ?
                    Sort.by("name").ascending() :
                    Sort.by("name").descending();
        }
        return (null == sort) ?
                PageRequest.of(searchModel.getPageNumber(), searchModel.getPageSize()) :
                PageRequest.of(searchModel.getPageNumber(), searchModel.getPageSize(), sort);
    }
}
