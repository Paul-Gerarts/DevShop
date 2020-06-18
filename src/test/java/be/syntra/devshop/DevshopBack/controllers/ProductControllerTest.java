package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.entities.Review;
import be.syntra.devshop.DevshopBack.entities.StarRating;
import be.syntra.devshop.DevshopBack.factories.SecurityUserFactory;
import be.syntra.devshop.DevshopBack.models.*;
import be.syntra.devshop.DevshopBack.security.configuration.CorsConfiguration;
import be.syntra.devshop.DevshopBack.security.configuration.WebSecurityConfig;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAccessDeniedHandler;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAuthenticationEntryPoint;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.security.services.SecurityUserService;
import be.syntra.devshop.DevshopBack.services.CategoryServiceImpl;
import be.syntra.devshop.DevshopBack.services.ProductServiceImpl;
import be.syntra.devshop.DevshopBack.services.SearchService;
import be.syntra.devshop.DevshopBack.services.StarRatingService;
import be.syntra.devshop.DevshopBack.services.utilities.*;
import be.syntra.devshop.DevshopBack.testutilities.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategoryList;
import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategory_Headphones;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.*;
import static be.syntra.devshop.DevshopBack.testutilities.ReviewUtils.*;
import static be.syntra.devshop.DevshopBack.testutilities.SearchModelUtils.getDummySearchModel;
import static be.syntra.devshop.DevshopBack.testutilities.SearchModelUtils.getDummySearchModelDto;
import static be.syntra.devshop.DevshopBack.testutilities.StarRatingUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({JsonUtils.class, WebSecurityConfig.class, CorsConfiguration.class, JWTTokenProvider.class, JWTAuthenticationEntryPoint.class, JWTAccessDeniedHandler.class, ProductMapper.class, CategoryMapper.class})
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private CategoryServiceImpl categoryService;

    @MockBean
    private SearchService searchService;

    @MockBean
    private StarRatingService ratingService;

    @MockBean
    private SearchModelMapper searchModelMapper;

    @MockBean
    private StarRatingMapper starRatingMapper;

    @Mock
    private SecurityUserFactory securityUserFactory;

    @MockBean
    private SecurityUserService securityUserService;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private ReviewMapper reviewMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    @WithMockUser
    void canGetProductByIdTest() throws Exception {
        // given
        final Product dummyProduct = createNonArchivedProduct();
        when(productService.findById(dummyProduct.getId())).thenReturn(dummyProduct);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/products/details/" + dummyProduct.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(dummyProduct)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(equalTo("post-its")))
                .andExpect(jsonPath("$.price").value(equalTo(1.00)));

        verify(productService, times(1)).findById(dummyProduct.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/products", "/products/update"})
    @WithMockUser
    void canSaveAndUpdateProductTest(String url) throws Exception {
        // given
        final ProductDto productDtoDummy = createProductDto();
        final Product productDummy = createNonArchivedProduct();
        when(productMapper.convertToProduct(any(ProductDto.class))).thenReturn(productDummy);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(productDtoDummy)));
        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(productDtoDummy.getName()))
                .andExpect(jsonPath("$.archived").value(productDtoDummy.isArchived()))
                .andExpect(jsonPath("$.price").value(1.00));

        verify(productService, times(1)).save(any(Product.class));
    }

    @Test
    @WithMockUser
    void retrieveAllCategoriesTest() throws Exception {
        // given
        final List<Category> categories = createCategoryList();
        final CategoryList categoryListDummy = categoryMapper.convertToCategoryList(categories);
        when(categoryService.findAll()).thenReturn(categories);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/products/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(categoryListDummy.getCategories())));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categories", hasSize(1)))
                .andExpect(jsonPath("$.categories[0].name").value(equalTo("Headphones")));

        verify(categoryService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    void retrieveAllProductsBySearchModelTest() throws Exception {
        // given
        final SearchModelDto searchModelDtoDummy = getDummySearchModelDto();
        final SearchModel searchModelDummy = getDummySearchModel();
        final ProductList productList = createDummyProductList();
        final ProductPage dummyProductPage = createProductPage();
        when(searchModelMapper.convertToSearchModel(any())).thenReturn(searchModelDummy);
        when(searchService.applySearchModel(any())).thenReturn(dummyProductPage);
        when(productMapper.convertToProductListObject(any(ProductPage.class))).thenReturn(productList);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/products/searching")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(searchModelDtoDummy)));
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(2)))
                .andExpect(jsonPath("$.products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$.products[0].price").value(equalTo(55.99)));

        verify(searchModelMapper, times(1)).convertToSearchModel(any(SearchModelDto.class));
        verify(searchService, times(1)).applySearchModel(any(SearchModel.class));
    }

    @Test
    @WithMockUser
    void canDeleteCategoryTest() throws Exception {
        // given
        Category category = createCategory_Headphones();
        doNothing().when(categoryService).delete(category.getId());

        // when
        ResultActions resultActions = mockMvc.perform(delete("/products/categories/" + category.getId()));

        // then
        resultActions
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).delete(category.getId());
    }

    @Test
    @WithMockUser
    void canFindCategoryByIdTest() throws Exception {
        // given
        Category category = createCategory_Headphones();
        CategoryDto categoryDto = categoryMapper.mapToCategoryDto(category);
        when(categoryService.findById(category.getId())).thenReturn(category);

        // when
        ResultActions resultActions = mockMvc.perform(get("/products/categories/" + category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(categoryDto)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(equalTo(category.getName())));

        verify(categoryService, times(1)).findById(category.getId());
    }

    @Test
    @WithMockUser
    void canFindProductsWithCorrespondingCategoryTest() throws Exception {
        // given
        Category category = createCategory_Headphones();
        List<Product> dummyProductList = List.of(createNonArchivedProduct(), createArchivedProduct());
        when(productService.findAllByCorrespondingCategory(category.getId())).thenReturn(dummyProductList);
        when(productMapper.convertToProductListObject(dummyProductList)).thenReturn(ProductList.builder().products(dummyProductList).build());

        // when
        ResultActions resultActions = mockMvc.perform(get("/products/all/" + category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(dummyProductList)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(2)))
                .andExpect(jsonPath("$.products[0].name").value(equalTo("post-its")))
                .andExpect(jsonPath("$.products[0].price").value(equalTo(1.00)))
                .andExpect(jsonPath("$.products[1].name").value(equalTo("post-its")))
                .andExpect(jsonPath("$.products[1].price").value(equalTo(1.00)));

        verify(productService, times(1)).findAllByCorrespondingCategory(category.getId());
    }

    @Test
    @WithMockUser
    void canSetNewCategoryTest() throws Exception {
        // given
        CategoryChangeDto categoryChangeDto = CategoryChangeDto.builder()
                .categoryToDelete(1L)
                .categoryToSet(2L)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/products/categories/set_category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(categoryChangeDto)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryToDelete").value(equalTo(1)))
                .andExpect(jsonPath("$.categoryToSet").value(equalTo(2)));

        verify(productService, times(1)).setNewCategory(any(), any());
    }

    @Test
    @WithMockUser
    void canUpdateCategoryTest() throws Exception {
        // given
        CategoryChangeDto categoryChangeDto = CategoryChangeDto.builder()
                .categoryToDelete(1L)
                .newCategoryName("Test")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/products/categories/update_category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(categoryChangeDto)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryToDelete").value(equalTo(1)))
                .andExpect(jsonPath("$.newCategoryName").value(equalTo("Test")));

        verify(categoryService, times(1)).updateCategory(any(), any());
    }

    @Test
    @WithMockUser
    void canGetStarRatingFromUserTest() throws Exception {
        // given
        final Product dummyProduct = createNonArchivedProduct();
        final StarRating rating = createRating();
        final StarRatingDto ratingDto = createRatingDto();
        when(ratingService.getRatingFromUser(dummyProduct.getId(), rating.getUserName())).thenReturn(rating);
        when(starRatingMapper.mapToDto(rating)).thenReturn(ratingDto);

        // when
        ResultActions resultActions =
                mockMvc.perform(get("/products/" + rating.getUserName() + "/ratings/" + dummyProduct.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rating").value(equalTo(rating.getRating())))
                .andExpect(jsonPath("$.userName").value(equalTo(rating.getUserName())));

        verify(ratingService, times(1)).getRatingFromUser(dummyProduct.getId(), rating.getUserName());
    }

    @Test
    @WithMockUser
    void canSubmitRatingTest() throws Exception {
        // given
        Product dummyProduct = createNonArchivedProduct();
        Set<StarRating> ratings = createRatingSet();
        StarRating rating = createRating();
        StarRatingDto starRatingDto = createRatingDto();
        dummyProduct.setRatings(ratings);
        when(productService.submitRating(rating, dummyProduct.getId())).thenReturn(dummyProduct);
        when(starRatingMapper.mapToStarRating(starRatingDto)).thenReturn(rating);

        // when
        ResultActions resultActions = mockMvc.perform(post("/products/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(rating)));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rating").value(equalTo(4.0)))
                .andExpect(jsonPath("$.userName").value(equalTo("lens.huygh@gmail.com")));

        verify(productService, times(1)).submitRating(any(), any());
    }

    @Test
    @WithMockUser
    void canGetRatingsFromProductTest() throws Exception {
        // given
        final Product dummyProduct = createNonArchivedProduct();
        final Set<StarRating> ratings = createRatingSet();
        final StarRatingSet ratingsDto = new StarRatingSet(ratings);
        when(productService.getAllRatingsFromProduct(dummyProduct.getId())).thenReturn(ratings);
        when(starRatingMapper.mapToStarRatingSet(ratings)).thenReturn(ratingsDto);

        // when
        ResultActions resultActions =
                mockMvc.perform(get("/products/ratings/" + dummyProduct.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ratings", hasSize(3)))
                .andExpect(jsonPath("$.ratings[0]").isNotEmpty())
                .andExpect(jsonPath("$.ratings[1]").isNotEmpty())
                .andExpect(jsonPath("$.ratings[2]").isNotEmpty());

        verify(productService, times(1)).getAllRatingsFromProduct(dummyProduct.getId());
        verify(starRatingMapper, times(1)).mapToStarRatingSet(ratings);
    }

    @Test
    @WithMockUser
    void canSubmitReviewTest() throws Exception {
        // given
        Product dummyProduct = createNonArchivedProduct();
        dummyProduct.setReviews(getReviewSetWithDummyReview());
        ReviewDto dummyReviewDto = getDummyReviewDto();
        final Review dummyReview = getDummyReview();
        when(productService.submitReview(dummyReview, 1L)).thenReturn(dummyProduct);
        when(reviewMapper.mapToReview(any(ReviewDto.class))).thenReturn(getDummyReview());

        // when
        ResultActions resultActions = mockMvc.perform(post("/products/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(dummyReviewDto)));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(equalTo(dummyReviewDto.getUserName())))
                .andExpect(jsonPath("$.reviewText").value(equalTo(dummyReviewDto.getReviewText())));

        verify(productService, times(1)).submitReview(any(), any());
    }

    @Test
    @WithMockUser
    void canUpdateReviewTest() throws Exception {
        // given
        Product dummyProduct = createNonArchivedProduct();
        Set<Review> reviewSet = new HashSet<>();
        reviewSet.add(getDummyReview());
        dummyProduct.setReviews(reviewSet);
        ReviewDto dummyReviewDto = getDummyReviewDto();
        final Review dummyReview = getDummyReview();
        when(productService.updateReview(dummyReview, 1L)).thenReturn(dummyProduct);
        when(reviewMapper.mapToReview(any(ReviewDto.class))).thenReturn(getDummyReview());

        // when
        ResultActions resultActions = mockMvc.perform(put("/products/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(dummyReviewDto)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(equalTo(dummyReviewDto.getUserName())))
                .andExpect(jsonPath("$.reviewText").value(equalTo(dummyReviewDto.getReviewText())));

        verify(productService, times(1)).updateReview(any(), any());
    }


    @Test
    @WithMockUser
    void canRemoveReviewTest() throws Exception {
        // given
        Product dummyProduct = createNonArchivedProduct();
        Set<Review> reviewSet = new HashSet<>();
        reviewSet.add(getDummyReview());
        dummyProduct.setReviews(reviewSet);
        ReviewDto dummyReviewDto = getDummyReviewDto();
        final Review dummyReview = getDummyReview();
        when(productService.removeReview(dummyReview, 1L)).thenReturn(dummyProduct);
        when(reviewMapper.mapToReview(any(ReviewDto.class))).thenReturn(getDummyReview());

        // when
        ResultActions resultActions = mockMvc.perform(delete("/products/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtils.asJsonString(dummyReviewDto)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(equalTo(dummyReviewDto.getUserName())))
                .andExpect(jsonPath("$.reviewText").value(equalTo(dummyReviewDto.getReviewText())));

        verify(productService, times(1)).removeReview(any(), any());
    }
}
