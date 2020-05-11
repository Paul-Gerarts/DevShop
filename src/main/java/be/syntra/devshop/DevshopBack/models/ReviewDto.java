package be.syntra.devshop.DevshopBack.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private String userName;
    private String productName;
    private String reviewText;
}
