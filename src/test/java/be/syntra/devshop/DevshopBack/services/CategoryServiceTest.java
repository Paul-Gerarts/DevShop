package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.repositories.CategoryRepository;
import be.syntra.devshop.DevshopBack.services.utilities.CategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategoryList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void canRetrieveCategoryListByNameTest() {
        // given
        String categoryName = "Headphones";
        List<Category> categoriesDummy = createCategoryList();
        when(categoryRepository.findAllByName(categoryName)).thenReturn(categoriesDummy);

        // when
        List<Category> result = categoryService.findAllBy(categoryName);

        // then
        assertThat(result.get(0).getName()).isEqualTo(categoryName);
        verify(categoryRepository, times(1)).findAllByName(categoryName);
    }

    @Test
    void canRetrieveAllCategoriesTest() {
        // given
        List<Category> categoriesDummy = createCategoryList();
        when(categoryRepository.findAllByOrderByNameAsc()).thenReturn(categoriesDummy);
        when(categoryMapper.convertToCategoryList(any())).thenReturn(new CategoryList(categoriesDummy));

        // when
        List<Category> result = categoryService.findAll();

        // then
        assertThat(result.size()).isEqualTo(categoriesDummy.size());
        verify(categoryRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    void canFindOneByNameTest() {
        // given
        Category category = Category.builder()
                .name("Headphones")
                .build();
        when(categoryRepository.findOneByName(category.getName())).thenReturn(category);

        // when
        Category result = categoryService.findOneByName(category.getName());

        // then
        assertThat(result).isEqualTo(category);
    }
}
