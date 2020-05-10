package be.syntra.devshop.DevshopBack.models;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchModelDto {

    private String searchRequest;
    private String description;
    private BigDecimal priceLow;
    private BigDecimal priceHigh;
    private boolean sortAscendingName;
    private boolean sortAscendingPrice;
    private boolean archivedView;
    private boolean searchResultView;
    private boolean searchFailure;
    private boolean activeFilters;
    private String appliedFiltersHeader;
    private Integer pageNumber;
    private Integer pageSize;
}
