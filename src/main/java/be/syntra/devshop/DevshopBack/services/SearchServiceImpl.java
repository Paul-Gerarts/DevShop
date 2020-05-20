package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductPage;
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
    private int defaultPageSize;
    @Value("${pagination.default.pageNumber}")
    private int defaultPageNumber;
    private final ProductService productService;

    @Autowired
    public SearchServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductPage applySearchModel(SearchModel searchModel) {
        log.info("searchModel -> {}", searchModel);
        Pageable pageable = getSortingPageable(setDefaultPaginationValues(searchModel));
        if (searchModel.isSearchResultView()) {
            if (StringUtils.hasText(searchModel.getSearchRequest())) {

                if (StringUtils.hasText(searchModel.getDescription())) {
                    return getSearchResultsAndMinMaxPrice(
                            productService.findAllNonArchivedBySearchTermAndDescriptionAndPriceBetween(searchModel.getSearchRequest(), searchModel.getDescription(), searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable)
                    );
                } else {
                    if (isPriceFilterActiveAndValid(searchModel)) {
                        return getSearchResultsAndMinMaxPrice(
                                productService.findAllNonArchivedBySearchTermAndPriceBetween(searchModel.getSearchRequest(), searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable),
                                productService.findMinPriceProductNonArchivedBySearchTermAndPriceBetween(searchModel.getSearchRequest(), searchModel.getPriceLow(), searchModel.getPriceHigh()),
                                productService.findMaxPriceProductNonArchivedBySearchTermAndPriceBetween(searchModel.getSearchRequest(), searchModel.getPriceLow(), searchModel.getPriceHigh())
                        );
                    } else {
                        return getSearchResultsAndMinMaxPrice(
                                productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchModel.getSearchRequest(), pageable),
                                productService.findMinPriceProductByNameContainingIgnoreCaseAndArchivedFalse(searchModel.getSearchRequest()),
                                productService.findMaxPriceProductByNameContainingIgnoreCaseAndArchivedFalse(searchModel.getSearchRequest())
                        );
                    }
                }
            }
            if (StringUtils.hasText(searchModel.getDescription())) {
                if (isPriceFilterActiveAndValid(searchModel)) {
                    return getSearchResultsAndMinMaxPrice(
                            productService.findAllNonArchivedByDescriptionAndPriceBetween(searchModel.getDescription(), searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable),
                            productService.findMinPriceProductNonArchivedByDescriptionAndPriceBetween(searchModel.getDescription(), searchModel.getPriceLow(), searchModel.getPriceHigh()),
                            productService.findMaxPriceProductNonArchivedByDescriptionAndPriceBetween(searchModel.getDescription(), searchModel.getPriceLow(), searchModel.getPriceHigh())
                    );
                } else {
                    return getSearchResultsAndMinMaxPrice(
                            productService.findAllByDescriptionAndByArchivedFalse(searchModel.getDescription(), pageable),
                            productService.findMinPriceProductByDescriptionAndByArchivedFalse(searchModel.getDescription()),
                            productService.findMaxPriceProductByDescriptionAndByArchivedFalse(searchModel.getDescription())
                    );
                }
            }
            if (isPriceFilterActiveAndValid(searchModel)) {
                return getSearchResultsAndMinMaxPrice(
                        productService.findAllArchivedFalseByPriceBetween(searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable),
                        productService.findMinPriceProductArchivedFalseByPriceBetween(searchModel.getPriceLow(), searchModel.getPriceHigh()),
                        productService.findMaxPriceProductArchivedFalseByPriceBetween(searchModel.getPriceLow(), searchModel.getPriceHigh())
                );
            }
        }

        if (searchModel.isArchivedView()) {
            return getSearchResultsAndMinMaxPrice(
                    productService.findAllByArchivedTrue(pageable),
                    productService.findMinPriceProductByArchivedTrue(),
                    productService.findMaxPriceProductByArchivedTrue()
            );
        }

        return getSearchResultsAndMinMaxPrice(
                productService.findAllByArchivedFalse(pageable),
                productService.findMinPriceProductByArchivedFalse(),
                productService.findMaxPriceProductByArchivedFalse()
        );
    }

    private ProductPage getSearchResultsAndMinMaxPrice(Page<Product> searchResults, Page<Product> productWithMinPrice, Page<Product> productWithMaxPrice) {
        if(searchFoundNoProducts(searchResults)){
            return ProductPage.builder()
                    .productPage(productService.findAllByArchivedFalse(searchResults.getPageable()))
                    .searchFailure(true)
                    .build();
        }
        return ProductPage.builder()
                .productPage(searchResults)
                .minPrice(getProductPrice(productWithMinPrice))
                .maxPrice(getProductPrice(productWithMaxPrice))
                .build();
    }

    private boolean searchFoundNoProducts(Page<Product> searchResults) {
        return searchResults.getContent().size() == 0;
    }

    private ProductPage getSearchResultsAndMinMaxPrice(Page<Product> searchResults) {
        return ProductPage.builder()
                .productPage(searchResults)
                .build();
    }

    private BigDecimal getProductPrice(Page<Product> productPage) {
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
        searchModel.setPageSize(defaultPageSize);
        searchModel.setPageNumber(defaultPageNumber);
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
            sort = (searchModel.isSortAscendingName()) ? Sort.by(Sort.Order.asc("name").ignoreCase()) : Sort.by(Sort.Order.desc("name").ignoreCase());
        }
        return (null == sort) ?
                PageRequest.of(searchModel.getPageNumber(), searchModel.getPageSize()) :
                PageRequest.of(searchModel.getPageNumber(), searchModel.getPageSize(), sort);
    }
}
