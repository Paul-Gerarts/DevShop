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
        Pageable pageable = getSortingPageable(setDefaultPaginationValues(searchModel));
        if (searchModel.isSearchResultView()) {
            if (StringUtils.hasText(searchModel.getSearchRequest())) {
                if (isPriceFilterActiveAndValid(searchModel)) {
                    return getSearchResultsAndMaxPrice(
                            productService.findAllNonArchivedBySearchTermAndPriceBetween(
                                    searchModel.getSearchRequest(), searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable),
                            productService.findMaxPriceProductNonArchivedBySearchTermAndPriceBetween(
                                    searchModel.getSearchRequest(), searchModel.getPriceLow(), searchModel.getPriceHigh())
                    );
                } else {
                    return getSearchResultsAndMaxPrice(
                            productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(
                                    searchModel.getSearchRequest(), pageable),
                            productService.findMaxPriceProductByNameContainingIgnoreCaseAndArchivedFalse(
                                    searchModel.getSearchRequest())
                    );
                }
            }
            if (StringUtils.hasText(searchModel.getDescription())) {
                if (isPriceFilterActiveAndValid(searchModel)) {
                    return getSearchResultsAndMaxPrice(
                            productService.findAllNonArchivedByDescriptionAndPriceBetween(searchModel.getDescription(), searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable),
                            productService.findMaxPriceProductNonArchivedByDescriptionAndPriceBetween(searchModel.getDescription(), searchModel.getPriceLow(), searchModel.getPriceHigh())
                    );
                } else {
                    return getSearchResultsAndMaxPrice(
                            productService.findAllByDescriptionAndByArchivedFalse(searchModel.getDescription(), pageable),
                            productService.findMaxPriceProductByDescriptionAndByArchivedFalse(searchModel.getDescription())
                    );
                }
            }
            if (isPriceFilterActiveAndValid(searchModel)) {
                return getSearchResultsAndMaxPrice(
                        productService.findAllArchivedFalseByPriceBetween(searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable),
                        productService.findMaxPriceProductArchivedFalseByPriceBetween(searchModel.getPriceLow(), searchModel.getPriceHigh())
                );
            }
        }

        if (searchModel.isArchivedView()) {
            return getSearchResultsAndMaxPrice(
                    productService.findAllByArchivedTrue(pageable),
                    productService.findMaxPriceProductByArchivedTrue()
            );
        }

        return getSearchResultsAndMaxPrice(
                productService.findAllByArchivedFalse(pageable),
                productService.findMaxPriceProductByArchivedFalse()
        );
    }

    private ProductPageAndMaxPrice getSearchResultsAndMaxPrice(Page<Product> searchResults, Page<Product> productWithMaxPrice) {
        return ProductPageAndMaxPrice.builder()
                .productPage(searchResults)
                .maxPrice(getProductMaxPrice(productWithMaxPrice))
                .build();
    }

    private BigDecimal getProductMaxPrice(Page<Product> productPage) {
        return productPage.getContent().stream()
                .findFirst()
                .map(Product::getPrice)
                .orElse(BigDecimal.ZERO);
    }

    private boolean isPriceFilterActiveAndValid(SearchModel searchModel) {
        return (searchModel.isActiveFilters()) &&
                (null != searchModel.getPriceLow()) &&
                (null != searchModel.getPriceHigh());
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

    private Pageable getSortingPageable(SearchModel searchModel) {
        Sort sort = null;
        if (searchModel.isPriceSortActive()) {
            sort = (searchModel.isSortAscendingPrice()) ? Sort.by("price").ascending() : Sort.by("price").descending();
        }
        if (searchModel.isNameSortActive()) {
            sort = (searchModel.isSortAscendingName()) ? Sort.by("name").ascending() : Sort.by("name").descending();
        }
        return (null == sort) ?
                PageRequest.of(searchModel.getPageNumber(), searchModel.getPageSize()) :
                PageRequest.of(searchModel.getPageNumber(), searchModel.getPageSize(), sort);
    }
}
