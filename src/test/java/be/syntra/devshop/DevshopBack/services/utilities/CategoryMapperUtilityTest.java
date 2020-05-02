package be.syntra.devshop.DevshopBack.services.utilities;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryDto;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.services.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategoryList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CategoryMapperUtilityTest {

    @InjectMocks
    private CategoryMapperUtility categoryMapperUtility;

    @Mock
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

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

    @Test
    void canMapToCategoryTest() {
        // given
        String categoryName = "Headphones";
        Category category = Category.builder()
                .name(categoryName)
                .build();
        List<String> categoryNames = List.of(categoryName);
        when(categoryService.findOneByName(categoryName)).thenReturn(category);

        // when
        List<Category> result = categoryMapperUtility.mapToCategory(categoryNames);

        // then
        assertThat(result.size()).isEqualTo(categoryNames.size());
        assertThat(result.get(0).getName()).isEqualTo(categoryNames.get(0));
    }

    @Test
    void canMapToCategoryDtoTest() {
        // given
        String categoryName = "Headphones";
        Category category = Category.builder()
                .id(1L)
                .name(categoryName)
                .build();

        // when
        CategoryDto result = categoryMapperUtility.mapToCategoryDto(category);

        // then
        assertThat(result.getId()).isEqualTo(category.getId());
        assertThat(result.getName()).isEqualTo(category.getName());
    }
}
