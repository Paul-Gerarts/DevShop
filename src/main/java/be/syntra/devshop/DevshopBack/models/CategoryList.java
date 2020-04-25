package be.syntra.devshop.DevshopBack.models;

import be.syntra.devshop.DevshopBack.entities.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryList {

    private List<Category> categories;
}
