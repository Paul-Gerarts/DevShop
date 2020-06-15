package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.Review;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.exceptions.ProductNotFoundException;
import be.syntra.devshop.DevshopBack.models.CategoryChangeDto;
import be.syntra.devshop.DevshopBack.models.SearchModel;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategory_Headphones;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.*;
import static be.syntra.devshop.DevshopBack.testutilities.ReviewUtils.*;
import static be.syntra.devshop.DevshopBack.testutilities.SearchModelUtils.getDummySearchModel;
import static be.syntra.devshop.DevshopBack.testutilities.StarRatingUtils.createRating;
import static be.syntra.devshop.DevshopBack.testutilities.StarRatingUtils.createRatingSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryServiceImpl categoryService;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveProductTest() {
        // given
        final Product product = createNonArchivedProduct();

        // when
        final Product resultProduct = productService.save(product);

        // then
        assertEquals(product, resultProduct);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void canGetProductByIdTest() {
        // given
        final Product dummyProduct = createNonArchivedProduct();
        when(productRepository.findById(dummyProduct.getId())).thenReturn(Optional.of(dummyProduct));

        // when
        final Product resultProduct = productService.findById(dummyProduct.getId());

        // then
        assertThat(resultProduct).isEqualTo(dummyProduct);
        verify(productRepository, times(1)).findById(dummyProduct.getId());
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void canGetProductByArchivedTest(boolean archived) {
        // given
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable dummyPageable = createDummyPageable();
        when(productRepository.findAllByArchived(archived, dummyPageable)).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultProductList = productService.findAllByArchived(archived, dummyPageable);

        // then
        assertEquals(resultProductList, dummyProductPage);
        verify(productRepository, times(1)).findAllByArchived(archived, dummyPageable);
    }

    @Test
    void canFindAllBySearchModelTest() {
        // given
        final SearchModel searchModel = getDummySearchModel();
        final Page<Product> dummyProductPage = createDummyProductPage();
        final Pageable pageable = createDummyPageable();
        when(productRepository.findAllBySearchModel(pageable,
                searchModel.getSearchRequest(),
                searchModel.getDescription(),
                searchModel.getPriceLow(),
                searchModel.getPriceHigh(),
                searchModel.getAverageRating(),
                searchModel.isArchivedView(),
                searchModel.getSelectedCategories(),
                searchModel.getSelectedCategories().size()
        )).thenReturn(dummyProductPage);

        // when
        final Page<Product> resultPage = productService.findAllBySearchModel(pageable, searchModel);

        // then
        assertEquals(resultPage, dummyProductPage);
        verify(productRepository, times(1)).findAllBySearchModel(pageable,
                searchModel.getSearchRequest(),
                searchModel.getDescription(),
                searchModel.getPriceLow(),
                searchModel.getPriceHigh(),
                searchModel.getAverageRating(),
                searchModel.isArchivedView(),
                searchModel.getSelectedCategories(),
                searchModel.getSelectedCategories().size()
        );
    }

    @Test
    void exceptionIsThrownWhenProductNotFoundTest() {
        // given
        String errorMessage = "product cannot be found!";
        when(productRepository.findById(1L)).thenThrow(new ProductNotFoundException(errorMessage));

        // when - then
        assertThrows(ProductNotFoundException.class, () -> productRepository.findById(1L));
    }

    @Test
    void canGetAllProductsWithCorrespondingCategoryTest() {
        // given
        Category category = createCategory_Headphones();
        List<Product> dummyProducts = List.of(createNonArchivedProduct(), createArchivedProduct());
        when(productRepository.findAllWithCorrespondingCategory(category.getId())).thenReturn(dummyProducts);

        // when
        List<Product> result = productService.findAllByCorrespondingCategory(category.getId());

        // then
        assertThat(result).isEqualTo(dummyProducts);
        verify(productRepository, times(1)).findAllWithCorrespondingCategory(category.getId());
    }

    @Test
    void canSetNewCategoryTest() {
        // given
        Category category = createCategory_Headphones();
        CategoryChangeDto categoryChangeDto = CategoryChangeDto.builder()
                .categoryToDelete(1L)
                .categoryToSet(2L)
                .build();
        List<Product> dummyProducts = List.of(createNonArchivedProduct(), createArchivedProduct());
        when(categoryService.findById(categoryChangeDto.getCategoryToSet())).thenReturn(category);
        when(productRepository.findAllWithCorrespondingCategory(category.getId())).thenReturn(dummyProducts);

        // when
        productService.setNewCategory(categoryChangeDto.getCategoryToDelete(), categoryChangeDto.getCategoryToSet());

        // then
        assertThat(dummyProducts.get(0).getCategories().get(0).getName()).isEqualTo(category.getName());
        verify(productRepository, times(1)).findAllWithCorrespondingCategory(category.getId());
        verify(productRepository, times(1)).saveAll(any());
        verify(categoryService, times(1)).findById(categoryChangeDto.getCategoryToSet());
    }

    @Test
    void canRemoveOneCategoryTest() {
        // given
        Category category = createCategory_Headphones();
        CategoryChangeDto categoryChangeDto = CategoryChangeDto.builder()
                .categoryToDelete(1L)
                .build();
        List<Product> dummyProducts = createDummyNonArchivedProductList();
        when(categoryService.findById(categoryChangeDto.getCategoryToDelete())).thenReturn(category);
        when(productRepository.findAllWithCorrespondingCategories(category.getId())).thenReturn(dummyProducts);

        // when
        productService.removeOneCategory(categoryChangeDto.getCategoryToDelete());

        // then
        assertThat(dummyProducts.get(0).getCategories().get(0).getName()).isNotEqualTo(category.getName());
        verify(productRepository, times(1)).findAllWithCorrespondingCategories(category.getId());
        verify(productRepository, times(1)).saveAll(any());
        verify(categoryService, times(1)).findById(categoryChangeDto.getCategoryToDelete());
    }

    @Test
    void canFindAverageRatingScoreForProductTest() {
        // given
        Set<StarRating> ratings = createRatingSet();
        when(productRepository.getProductRating(1L)).thenReturn(Optional.of(3D));

        // when
        Double result = productService.getProductRating(1L);
        Double doubleCheck = ratings.parallelStream()
                .mapToDouble(StarRating::getRating)
                .average()
                .orElse(Double.NaN);

        // then
        assertThat(result).isEqualTo(3D);
        assertThat(result).isEqualTo(doubleCheck);
        verify(productRepository, times(1)).getProductRating(1L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"lens.huygh@gmail.com", "user@email.com"})
    void canSubmitRatingWithOnlyUniqueUserNameTest(String userName) {
        // given
        Product product = createNonArchivedProduct();
        Set<StarRating> ratings = createRatingSet();
        product.setRatings(ratings);
        StarRating rating = createRating();
        rating.setUserName(userName);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // when
        Product result = productService.submitRating(rating, product.getId());

        // then
        assertThat(result.getRatings().size()).isEqualTo(desiredSetSize(userName));
        assertThat(result.getRatings().contains(rating)).isTrue();
        verify(productRepository, times(1)).findById(product.getId());
        verify(productRepository, times(1)).save(any());
    }

    private int desiredSetSize(String userName) {
        return "user@email.com".equals(userName)
                ? 4
                : 3;
    }

    @Test
    void canFindRatingsForProductTest() {
        // given
        Set<StarRating> ratings = createRatingSet();
        when(productRepository.findAllStarRatingFromProduct(1L)).thenReturn(ratings);

        // when
        Set<StarRating> resultRatings = productService.getAllRatingsFromProduct(1L);

        // then
        assertThat(resultRatings.size()).isEqualTo(ratings.size());
        assertThat(resultRatings.containsAll(ratings)).isTrue();
        verify(productRepository, times(1)).findAllStarRatingFromProduct(1L);
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void canFindProductRoundedPriceMaxTest(boolean archived) {
        // given
        Double price = 5D;
        when(productRepository.findRoundedMaxPrice(archived)).thenReturn(price);

        // when
        Double roundedResult = productService.findRoundedMaxPrice(archived);

        // then
        assertThat(roundedResult).isEqualTo(price);
        verify(productRepository, times(1)).findRoundedMaxPrice(archived);
    }

    @Test
    void canSubmitReviewTest() {
        // given
        Product dummyProduct = createNonArchivedProduct();
        final Review dummyReview = getDummyReview();
        dummyProduct.setReviews(getReviewSetWithDummyOtherReview());
        when(productRepository.findById(dummyProduct.getId())).thenReturn(Optional.of(dummyProduct));
        when(productRepository.save(any(Product.class))).thenReturn(dummyProduct);
        // when
        Product resultProduct = productService.submitReview(dummyReview, dummyProduct.getId());

        // then
        assertEquals(dummyProduct.getReviews(), resultProduct.getReviews());
    }

    @Test
    void canRemoveReviewTest() {
        // given
        Product dummyProduct = createNonArchivedProduct();
        final Review dummyReview = getDummyReview();
        Set<Review> reviewSet = new HashSet<>();
        reviewSet.add(dummyReview);
        dummyProduct.setReviews(reviewSet);
        when(productRepository.findById(dummyProduct.getId())).thenReturn(Optional.of(dummyProduct));
        when(productRepository.save(any(Product.class))).thenReturn(dummyProduct);
        // when
        Product resultProduct = productService.removeReview(dummyReview, dummyProduct.getId());

        // then
        assertEquals(dummyProduct.getReviews(), resultProduct.getReviews());
    }

    @Test
    void canUpdateReviewTest() {
        // given
        Product dummyProduct = createNonArchivedProduct();
        Review dummyReview = getDummyReview();
        Review dummyOtherReview = getDummyOtherReview();
        dummyOtherReview.setUserName(dummyReview.getUserName());
        Set<Review> reviewSet = new HashSet<>();
        reviewSet.add(dummyReview);
        dummyProduct.setReviews(reviewSet);
        when(productRepository.findById(dummyProduct.getId())).thenReturn(Optional.of(dummyProduct));
        when(productRepository.save(any(Product.class))).thenReturn(dummyProduct);
        // when
        Product resultProduct = productService.updateReview(dummyOtherReview, dummyProduct.getId());

        // then
        assertEquals(((Review) resultProduct.getReviews().toArray()[0]).getReviewText(), dummyOtherReview.getReviewText());
    }

}
