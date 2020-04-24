package be.syntra.devshop.DevshopBack.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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
    private List<String> categoryNames;
}
