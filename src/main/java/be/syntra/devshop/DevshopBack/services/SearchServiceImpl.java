package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.SearchModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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
    public List<Product> applySearchModel(SearchModel searchModel) {
        log.info("searchModel -> {}", searchModel);
        Pageable pageable = setSorting(setDefaultPaginationValues(searchModel));

        if (searchModel.isSearchResultView()) {
            if (StringUtils.hasText(searchModel.getSearchRequest())) {
                if (priceFiltersActiveAndValid(searchModel)) {
                    return productService.findAllNonArchivedBySearchTermAndPriceBetween(searchModel.getSearchRequest(), searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable).getContent();
                } else {
                    return productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchModel.getSearchRequest(), pageable).getContent();
                }
            }
            if (StringUtils.hasText(searchModel.getDescription())) {
                if (priceFiltersActiveAndValid(searchModel)) {
                    return productService.findAllNonArchivedByDescriptionAndPriceBetween(searchModel.getDescription(), searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable).getContent();
                } else {
                    return productService.findAllByDescriptionAndByArchivedFalse(searchModel.getDescription(), pageable).getContent();
                }
            }
            if (priceFiltersActiveAndValid(searchModel)) {
                return productService.findAllArchivedFalseByPriceBetween(searchModel.getPriceLow(), searchModel.getPriceHigh(), pageable).getContent();
            }
        }

        if (searchModel.isArchivedView()) {
            return productService.findAllByArchivedTrue(pageable).getContent();
        }

        return productService.findAllByArchivedFalse(pageable).getContent();
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
