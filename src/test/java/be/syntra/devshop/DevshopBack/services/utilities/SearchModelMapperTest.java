package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.models.SearchModel;
import be.syntra.devshop.DevshopBack.models.SearchModelDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchModelMapperTest {

    private SearchModelMapper searchModelMapper = new SearchModelMapper();

    @Test
    void convertToSearchModelTest() {
        // given
        final SearchModelDto searchModelDto = SearchModelDto.builder()
                .activeFilters(true)
                .appliedFiltersHeader("test")
                .archivedView(false)
                .description("description")
                .priceHigh(BigDecimal.TEN)
                .priceLow(BigDecimal.ZERO)
                .searchFailure(false)
                .searchRequest("request")
                .searchResultView(true)
                .sortAscendingName(true)
                .sortAscendingPrice(false)
                .nameSortActive(true)
                .priceSortActive(false)
                .pageNumber(5)
                .pageSize(2)
                .build();

        // when
        final SearchModel resultSearchModel = searchModelMapper.convertToSearchModel(searchModelDto);

        // then
        assertEquals(resultSearchModel.getClass(), SearchModel.class);
        assertEquals(resultSearchModel.isActiveFilters(), searchModelDto.isActiveFilters());
        assertEquals(resultSearchModel.getAppliedFiltersHeader(), searchModelDto.getAppliedFiltersHeader());
        assertEquals(resultSearchModel.isArchivedView(), searchModelDto.isArchivedView());
        assertEquals(resultSearchModel.getDescription(), searchModelDto.getDescription());
        assertEquals(resultSearchModel.getPriceHigh(), searchModelDto.getPriceHigh());
        assertEquals(resultSearchModel.getPriceLow(), searchModelDto.getPriceLow());
        assertEquals(resultSearchModel.isSearchFailure(), searchModelDto.isSearchFailure());
        assertEquals(resultSearchModel.getSearchRequest(), searchModelDto.getSearchRequest());
        assertEquals(resultSearchModel.isSearchResultView(), searchModelDto.isSearchResultView());
        assertEquals(resultSearchModel.isSortAscendingName(), searchModelDto.isSortAscendingName());
        assertEquals(resultSearchModel.isSortAscendingPrice(), searchModelDto.isSortAscendingPrice());
        assertEquals(resultSearchModel.isNameSortActive(), searchModelDto.isNameSortActive());
        assertEquals(resultSearchModel.isPriceSortActive(), searchModelDto.isPriceSortActive());
        assertEquals(resultSearchModel.getPageNumber(), searchModelDto.getPageNumber());
        assertEquals(resultSearchModel.getPageSize(), searchModelDto.getPageSize());
    }
}
