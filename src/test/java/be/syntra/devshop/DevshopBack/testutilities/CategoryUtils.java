package be.syntra.devshop.DevshopBack.testutilities;

import be.syntra.devshop.DevshopBack.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryUtils {

    public static List<Category> createCategoryList() {
        return List.of(Category.builder()
                .name("Headphones")
                .build());
    }

    public static List<Category> createMutableCategoryList() {
        List<Category> categories = new ArrayList<>();
        categories.add(createCategory_Offices());
        categories.add(createCategory_Headphones());
        return categories;
    }

    public static Category createCategory_Headphones() {
        return Category.builder()
                .id(1L)
                .name("Headphones")
                .build();
    }

    public static Category createCategory_Offices() {
        return Category.builder()
                .id(2L)
                .name("Offices")
                .build();
    }
}
