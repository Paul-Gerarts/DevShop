package be.syntra.devshop.DevshopBack.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartContentProductDto {
    private Long id;
    private Long productId;
    private Integer count;
}
