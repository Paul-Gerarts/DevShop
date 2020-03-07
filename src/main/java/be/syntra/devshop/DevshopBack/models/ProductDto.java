package be.syntra.devshop.DevshopBack.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {

    private String name;
    private BigDecimal price;

}
