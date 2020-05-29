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
        final boolean archived = searchModel.isArchivedView();
        final Pageable pageable = getSortingPageable(setDefaultPaginationValues(searchModel));
        final BigDecimal minPrice = getMinPrice(searchModel);
        final BigDecimal maxPrice = getMaxPrice(searchModel, archived);

        searchModel.setPriceLow(minPrice);
        searchModel.setPriceHigh(maxPrice);

        return getProductPage(
                productService.findAllBySearchModel(pageable, searchModel),
                minPrice,
                maxPrice,
                archived
        );
    }

    private ProductPage getProductPage(Page<Product> searchResults, BigDecimal minPrice, BigDecimal maxPrice, boolean archived) {

        boolean searchFailure = searchResults.getContent().isEmpty();

        Page<Product> result = searchFailure
                ? productService.findAllByArchived(archived, searchResults.getPageable())
                : searchResults;

        return ProductPage.builder()
                .productPage(result)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .searchFailure(searchFailure)
                .hasNext(result.hasNext())
                .hasPrevious(result.hasPrevious())
                .currentPage(result.getNumber())
                .totalPages(result.getTotalPages())
                .build();
    }

    private BigDecimal getMinPrice(SearchModel searchModel) {
        return hasPrice(searchModel)
                ? BigDecimal.ZERO
                : searchModel.getPriceLow();
    }

    private BigDecimal getMaxPrice(SearchModel searchModel, boolean archived) {
        return hasPrice(searchModel)
                ? BigDecimal.valueOf(productService.findRoundedMaxPrice(archived))
                : searchModel.getPriceHigh();
    }

    private boolean hasPrice(SearchModel searchModel) {
        return null == searchModel.getPriceLow() || null == searchModel.getPriceHigh();
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
