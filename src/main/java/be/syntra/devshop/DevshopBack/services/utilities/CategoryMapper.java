package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    @Autowired
    private CategoryServiceImpl categoryService;

    public CategoryList convertToCategoryList(List<Category> categories) {
        return new CategoryList(categories);
    }

    public List<Category> mapToCategory(List<String> categoryNames) {
        List<Category> categories = new ArrayList<>();
        categoryNames.forEach(categoryName -> categories.add(categoryService.findOneByName(categoryName)));
        return categories;
    }


}
