package be.syntra.devshop.DevshopBack.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartContentDto {
    private ProductDto productDto;
    private Integer count;
}
