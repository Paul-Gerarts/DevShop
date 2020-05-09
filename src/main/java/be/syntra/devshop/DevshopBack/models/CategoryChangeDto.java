package be.syntra.devshop.DevshopBack.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryChangeDto {

    private Long categoryToDelete;
    private Long categoryToSet;
    private String newCategoryName;
}
