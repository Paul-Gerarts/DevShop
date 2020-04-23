package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategoryList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryMapperUtilityTest {

    private final CategoryMapperUtility categoryMapperUtility = new CategoryMapperUtility();

    @Test
    void canConvertToCategoryListTest() {
        // given
        List<Category> categories = createCategoryList();

        // when
        CategoryList result = categoryMapperUtility.convertToCategoryList(categories);

        // then
        assertThat(result.getClass()).isEqualTo(CategoryList.class);
        assertThat(result.getCategories().size()).isEqualTo(categories.size());
        assertThat(result.getCategories().get(0).getName()).isEqualTo(categories.get(0).getName());
    }
}
