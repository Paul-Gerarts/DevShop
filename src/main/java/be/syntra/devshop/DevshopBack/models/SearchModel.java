package be.syntra.devshop.DevshopBack.models;

import lombok.*;

import java.math.BigDecimal;

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
    private boolean sortAscendingName;
    private boolean sortAscendingPrice;
    private boolean archivedView;
    private boolean searchResultView;
    private boolean searchFailure;
    private boolean activeFilters;
    private String appliedFiltersHeader;
    private Integer pageNumber;
    private Integer pageSize;

    @Override
    public String toString() {
        return "SearchModel{" + "\n" +
                "searchRequest='" + searchRequest + '\'' + "\n" +
                ", description='" + description + '\'' + "\n" +
                ", priceLow=" + priceLow + "\n" +
                ", priceHigh=" + priceHigh + "\n" +
                ", sortAscendingName=" + sortAscendingName + "\n" +
                ", sortAscendingPrice=" + sortAscendingPrice + "\n" +
                ", archivedView=" + archivedView + "\n" +
                ", searchResultView=" + searchResultView + "\n" +
                ", searchFailure=" + searchFailure + "\n" +
                ", activeFilters=" + activeFilters + "\n" +
                ", appliedFiltersHeader='" + appliedFiltersHeader + '\'' + "\n" +
                ", pageNumber=" + pageNumber + "\n" +
                ", pageSize=" + pageSize + "\n" +
                '}';
    }
}
