package be.syntra.devshop.DevshopBack.models;

import be.syntra.devshop.DevshopBack.entities.Product;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductList {
    private List<Product> products;
    private BigDecimal searchResultMinPrice;
    private BigDecimal searchResultMaxPrice;
    private boolean searchFailure;
    private boolean hasNext;
    private boolean hasPrevious;
    private int currentPage;
    private int totalPages;
}
