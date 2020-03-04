package be.syntra.devshop.DevshopBack.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private BigDecimal price;
}
