package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.models.SearchModel;
import be.syntra.devshop.DevshopBack.models.SearchModelDto;

import java.math.BigDecimal;

public class SearchModelUtils {
    public static SearchModelDto getDummySearchModelDto() {
        return SearchModelDto.builder()
                .searchFailure(false)
                .searchRequest("")
                .searchResultView(false)
                .activeFilters(false)
                .appliedFiltersHeader("")
                .archivedView(false)
                .description("")
                .priceHigh(new BigDecimal("99999.99"))
                .priceLow(BigDecimal.ZERO)
                .sortAscendingName(false)
                .sortAscendingPrice(false)
                .build();
    }

    public static SearchModel getDummySearchModel() {
        return SearchModel.builder()
                .searchRequest("")
                .description("")
                .priceLow(BigDecimal.ZERO)
                .priceHigh(BigDecimal.TEN)
                .sortAscendingName(false)
                .sortAscendingPrice(false)
                .nameSortActive(false)
                .priceSortActive(false)
                .archivedView(false)
                .searchResultView(false)
                .searchFailure(false)
                .activeFilters(false)
                .appliedFiltersHeader("")
                .pageNumber(0)
                .pageSize(10)
                .build();
    }
}
