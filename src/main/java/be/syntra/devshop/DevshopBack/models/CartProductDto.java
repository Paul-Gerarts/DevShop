package be.syntra.devshop.DevshopBack.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartProductDto {
    private ProductDto productDto;
    private Integer count;
}
