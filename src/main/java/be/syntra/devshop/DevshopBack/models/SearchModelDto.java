package be.syntra.devshop.DevshopBack.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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
    private boolean nameSortActive;
    private boolean priceSortActive;
    private boolean archivedView;
    private boolean archivedSearchSwitch;
    private boolean searchResultView;
    private boolean searchFailure;
    private boolean activeFilters;
    private String appliedFiltersHeader;
    private Integer pageNumber;
    private Integer pageSize;
    private List<String> selectedCategories;
    private double averageRating;
}
