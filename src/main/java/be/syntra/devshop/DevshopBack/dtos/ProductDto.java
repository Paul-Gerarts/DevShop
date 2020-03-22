package be.syntra.devshop.DevshopBack.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@ToString
public class ProductDto {

    private String name;
    private BigDecimal price;

}
