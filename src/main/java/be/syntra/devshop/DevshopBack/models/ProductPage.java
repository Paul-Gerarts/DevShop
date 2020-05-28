package be.syntra.devshop.DevshopBack.models;

import be.syntra.devshop.DevshopBack.entities.Product;
import lombok.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPage {
    private Page<Product> productPage;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private boolean searchFailure;
    private boolean hasNext;
    private boolean hasPrevious;
    private int currentPage;
    private int totalPages;
}
