package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapperUtility {

    public CategoryList convertToCategoryList(List<Category> categories) {
        return new CategoryList(categories);
    }
}
