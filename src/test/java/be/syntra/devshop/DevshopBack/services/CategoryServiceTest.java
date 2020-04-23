package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.repositories.CategoryRepository;
import be.syntra.devshop.DevshopBack.services.utilities.CategoryMapperUtility;
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
    private CategoryMapperUtility categoryMapperUtility;

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
        when(categoryRepository.findAll()).thenReturn(categoriesDummy);
        when(categoryMapperUtility.convertToCategoryList(any())).thenReturn(new CategoryList(categoriesDummy));

        // when
        List<Category> result = categoryService.findAll().getCategories();

        // then
        assertThat(result.size()).isEqualTo(categoriesDummy.size());
        verify(categoryRepository, times(1)).findAll();
    }
}
