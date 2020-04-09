package be.syntra.devshop.DevshopBack.models;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private boolean archived;
}
