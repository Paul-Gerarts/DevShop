package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.Category;

import java.util.List;

public class CategoryUtils {

    public static List<Category> createCategoryList() {
        return List.of(Category.builder()
                .name("Headphones")
                .build());
    }

    public static Category createCategory() {
        return Category.builder()
                .id(1L)
                .name("Headphones")
                .build();
    }
}
