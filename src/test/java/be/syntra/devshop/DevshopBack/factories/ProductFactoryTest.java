package be.syntra.devshop.DevshopBack.factories;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.services.CategoryServiceImpl;
import be.syntra.devshop.DevshopBack.services.StarRatingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategoryList;
import static be.syntra.devshop.DevshopBack.testutilities.StarRatingUtils.createRatingSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProductFactoryTest {

    @InjectMocks
    private final ProductFactory productFactory = new ProductFactory();

    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private StarRatingServiceImpl ratingService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void canCreateProductTest() {
        // given
        String productName = "test";
        BigDecimal productPrice = new BigDecimal(5);
        String productDescription = "description";
        boolean archived = false;
        List<Category> categories = createCategoryList();
        Double averageRating = 0D;

        // when
        Product resultProduct = productFactory.of(productName, productPrice, productDescription, archived, categories, averageRating, Collections.emptySet());

        // then
        assertThat(resultProduct.getClass()).isEqualTo(Product.class);
        assertThat(resultProduct.getName()).isEqualTo(productName);
        assertThat(resultProduct.getPrice()).isEqualTo(productPrice);
        assertThat(resultProduct.isArchived()).isEqualTo(archived);
        assertThat(resultProduct.getCategories().get(0).getName()).isEqualTo(categories.get(0).getName());
        assertThat(resultProduct.getDescription()).isEqualTo(productDescription);
    }

    @Test
    void canCreateProductsByRandomAmountTest() {
        // given
        int amountOfProductsToGenerate = new Random().nextInt(100);
        List<Category> categories = createCategoryList();
        Set<StarRating> ratings = createRatingSet();
        when(categoryService.findAll()).thenReturn(categories);
        when(ratingService.findAll()).thenReturn(ratings);

        // when
        List<Product> result = productFactory.ofRandomProducts(amountOfProductsToGenerate);

        // then
        assertThat(result.size()).isEqualTo(amountOfProductsToGenerate - 1);
    }
}
