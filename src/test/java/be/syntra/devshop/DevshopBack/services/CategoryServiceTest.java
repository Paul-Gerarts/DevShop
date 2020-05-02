package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.exceptions.CategoryNotFoundException;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.repositories.CategoryRepository;
import be.syntra.devshop.DevshopBack.services.utilities.CategoryMapperUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategory;
import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategoryList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        when(categoryRepository.findAllByOrderByNameAsc()).thenReturn(categoriesDummy);
        when(categoryMapperUtility.convertToCategoryList(any())).thenReturn(new CategoryList(categoriesDummy));

        // when
        List<Category> result = categoryService.findAll().getCategories();

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

    @Test
    void deleteCategoryTest() {
        // given
        Category category = createCategory();
        doNothing().when(categoryRepository).delete(category);
        when(categoryRepository.findById(category.getId())).thenReturn(of(category));

        // when
        categoryService.delete(category.getId());

        // then
        verify(categoryRepository, times(1)).findById(category.getId());
    }

    @Test
    void canFindCategoryById() {
        // given
        Category category = createCategory();
        when(categoryRepository.findById(category.getId())).thenReturn(of(category));

        // when
        Category result = categoryService.findById(category.getId());

        // then
        assertThat(result.getName()).isEqualTo(category.getName());
        assertThat(result.getId()).isEqualTo(category.getId());
    }

    @Test
    void willThrowExceptionWhenCategoryNotFoundTest() {
        // given
        Category category = createCategory();
        when(categoryRepository.findById(category.getId())).thenThrow(new CategoryNotFoundException("not found"));

        // when

        // then
        assertThrows(CategoryNotFoundException.class, () -> categoryRepository.findById(category.getId()));
    }
}
