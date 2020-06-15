package be.syntra.devshop.DevshopBack.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchModel {

    private String searchRequest;
    private String description;
    private BigDecimal priceLow;
    private BigDecimal priceHigh;
    private double averageRating;
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

    @Override
    public String toString() {
        return "SearchModel{" + "\n" +
                "searchRequest='" + searchRequest + '\'' + "\n" +
                "description='" + description + '\'' + "\n" +
                "priceLow=" + priceLow + "\n" +
                "priceHigh=" + priceHigh + "\n" +
                "averageRating=" + averageRating + "\n" +
                "sortAscendingName=" + sortAscendingName + "\n" +
                "sortAscendingPrice=" + sortAscendingPrice + "\n" +
                "nameSortActive=" + nameSortActive + "\n" +
                "priceSortActive=" + priceSortActive + "\n" +
                "archivedView=" + archivedView + "\n" +
                "searchResultView=" + searchResultView + "\n" +
                "searchFailure=" + searchFailure + "\n" +
                "activeFilters=" + activeFilters + "\n" +
                "appliedFiltersHeader='" + appliedFiltersHeader + '\'' + "\n" +
                "pageNumber=" + pageNumber + "\n" +
                "pageSize=" + pageSize + "\n" +
                "selectedCategories=" + selectedCategories + "\n" +
                '}';
    }
}
