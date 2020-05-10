package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.models.SearchModel;
import be.syntra.devshop.DevshopBack.models.SearchModelDto;
import org.springframework.stereotype.Component;

@Component
public class SearchModelMapper {

    public SearchModel convertToSearchModel(SearchModelDto searchModelDto){
        return SearchModel.builder()
                .activeFilters(searchModelDto.isActiveFilters())
                .appliedFiltersHeader(searchModelDto.getAppliedFiltersHeader())
                .archivedView(searchModelDto.isArchivedView())
                .description(searchModelDto.getDescription())
                .priceHigh(searchModelDto.getPriceHigh())
                .priceLow(searchModelDto.getPriceLow())
                .searchFailure(searchModelDto.isSearchFailure())
                .searchRequest(searchModelDto.getSearchRequest())
                .searchResultView(searchModelDto.isSearchResultView())
                .sortAscendingName(searchModelDto.isSortAscendingName())
                .sortAscendingPrice(searchModelDto.isSortAscendingPrice())
                .pageNumber(searchModelDto.getPageNumber())
                .pageSize(searchModelDto.getPageSize())
                .build();
    }
}
