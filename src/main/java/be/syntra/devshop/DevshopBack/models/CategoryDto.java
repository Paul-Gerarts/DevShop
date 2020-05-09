package be.syntra.devshop.DevshopBack.models;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class CategoryDto {

    private Long id;
    private String name;
}
