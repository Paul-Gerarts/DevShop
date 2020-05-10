package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.SearchModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    private final ProductService productService;

    @Autowired
    public SearchServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Product> applySearchModel(SearchModel searchModel) {
        log.info("searchModel -> {}",searchModel);
        Pageable pageable = PageRequest.of(searchModel.getPageNumber(), searchModel.getPageSize());
        if (searchModel.isSortAscendingPrice()) {
            log.info("applySearchModel() isSortAscendingPrice-> {}",searchModel.isSortAscendingPrice());
            pageable = PageRequest.of(searchModel.getPageNumber(), searchModel.getPageSize(), Sort.by("price").ascending());
        }
        if (searchModel.isSortAscendingName()) {
            log.info("applySearchModel() isSortAscendingName -> {}",searchModel.isSortAscendingName());
            pageable = PageRequest.of(searchModel.getPageNumber(), searchModel.getPageSize(), Sort.by("name").ascending());
        }
        if (searchModel.isSearchResultView()) {
            log.info("applySearchModel() isSearchResultView -> {}",searchModel.isSearchResultView());
            if (StringUtils.hasText(searchModel.getSearchRequest())) {
                log.info("applySearchModel() getSearchRequest -> {}",searchModel.getSearchRequest());
                return productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchModel.getSearchRequest(), pageable).getContent();
            }
            if (StringUtils.hasText(searchModel.getDescription())) {
                log.info("applySearchModel() getDescription -> {}",searchModel.getDescription());
                return productService.findAllByDescriptionAndByArchivedFalse(searchModel.getDescription(), pageable).getContent();
            }
        }
        if (searchModel.isArchivedView()) {
            log.info("applySearchModel() isArchivedView -> {}",searchModel.isArchivedView());
            return productService.findAllByArchivedTrue(pageable).getContent();
        }
        log.info("applySearchModel() -> empty");
        return productService.findAllByArchivedFalse(pageable).getContent();
    }
}
