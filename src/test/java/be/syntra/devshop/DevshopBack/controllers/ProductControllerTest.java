package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Category;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.factories.SecurityUserFactory;
import be.syntra.devshop.DevshopBack.models.CategoryDto;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.security.configuration.CorsConfiguration;
import be.syntra.devshop.DevshopBack.security.configuration.WebSecurityConfig;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAccessDeniedHandler;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAuthenticationEntryPoint;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.security.services.SecurityUserService;
import be.syntra.devshop.DevshopBack.services.CategoryServiceImpl;
import be.syntra.devshop.DevshopBack.services.ProductServiceImpl;
import be.syntra.devshop.DevshopBack.services.utilities.CategoryMapperUtility;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility;
import be.syntra.devshop.DevshopBack.testutilities.JsonUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategory;
import static be.syntra.devshop.DevshopBack.testutilities.CategoryUtils.createCategoryList;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({JsonUtils.class, WebSecurityConfig.class, CorsConfiguration.class, JWTTokenProvider.class, JWTAuthenticationEntryPoint.class, JWTAccessDeniedHandler.class, ProductMapperUtility.class, CategoryMapperUtility.class})
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

    @Mock
    private SecurityUserFactory securityUserFactory;

    @MockBean
    private SecurityUserService securityUserService;

    @Autowired
    private ProductMapperUtility productMapperUtility;

    @Autowired
    private CategoryMapperUtility categoryMapperUtility;

    @Test
    @WithMockUser
    void testRetrieveAllNonArchivedProductsEndpoint() throws Exception {
        // given
        ProductList dummyProductList = productMapperUtility.convertToProductListObject(createDummyNonArchivedProductList());
        when(productService.findAllByArchivedFalse()).thenReturn(dummyProductList);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(dummyProductList)));
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(2)))
                .andExpect(jsonPath("$.products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$.products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$.products[1].name", is("product")))
                .andExpect(jsonPath("$.products[1].price", is(110)));

        verify(productService, times(1)).findAllByArchivedFalse();
    }

    @Test
    @WithMockUser
    void testRetrieveAllArchivedProductsEndpoint() throws Exception {
        // given
        ProductList dummyProductList = productMapperUtility.convertToProductListObject(createDummyArchivedProductList());
        when(productService.findAllByArchivedTrue()).thenReturn(dummyProductList);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/products/archived")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(dummyProductList.getProducts())));
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(2)))
                .andExpect(jsonPath("$.products[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$.products[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$.products[0].archived", is(true)))
                .andExpect(jsonPath("$.products[1].name", is("product")))
                .andExpect(jsonPath("$.products[1].price", is(110)))
                .andExpect(jsonPath("$.products[1].archived", is(true)));

        verify(productService, times(1)).findAllByArchivedTrue();
    }

    @Test
    @WithMockUser
    void createProductEndpoint() throws Exception {
        // given
        ProductDto productDtoDummy = createProductDto();
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(productDtoDummy))
                );
        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(productDtoDummy.getName()))
                .andExpect(jsonPath("$.price").value(1.00));

        verify(productService, times(1)).save(productDtoDummy);
    }

    @Test
    @WithMockUser
    void canGetProductByIdTest() throws Exception {
        // given
        Product dummyProduct = createNonArchivedProduct();
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

    @Test
    @WithMockUser
    void canUpdateProductTest() throws Exception {
        // given
        ProductDto productDtoDummy = createProductDto();

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/products/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(productDtoDummy)));
        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(productDtoDummy.getName()))
                .andExpect(jsonPath("$.archived").value(productDtoDummy.isArchived()))
                .andExpect(jsonPath("$.price").value(1.00));

        verify(productService, times(1)).save(productDtoDummy);
    }

    @Test
    @WithMockUser
    void retrieveAllProductsFoundBySearchRequestTest() throws Exception {
        // given
        String searchRequest = "POst";
        ProductList dummyProductList = productMapperUtility.convertToProductListObject(List.of(createNonArchivedProduct()));
        when(productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest)).thenReturn(dummyProductList);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/products/search/" + searchRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonUtils.asJsonString(dummyProductList.getProducts())));
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(1)))
                .andExpect(jsonPath("$.products[0].name").value(equalTo("post-its")))
                .andExpect(jsonPath("$.products[0].price").value(equalTo(1.00)));

        verify(productService, times(1)).findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest);
    }

    @Test
    @WithMockUser
    void retrieveAllCategoriesTest() throws Exception {
        // given
        List<Category> categories = createCategoryList();
        CategoryList categoryListDummy = categoryMapperUtility.convertToCategoryList(categories);
        when(categoryService.findAll()).thenReturn(categoryListDummy);

        // then
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
    void canDeleteCategoryTest() throws Exception {
        // given
        Category category = createCategory();
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
        Category category = createCategory();
        CategoryDto categoryDto = categoryMapperUtility.mapToCategoryDto(category);
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
        Category category = createCategory();
        ProductList dummyProductList = productMapperUtility.convertToProductListObject(List.of(createNonArchivedProduct(), createArchivedProduct()));
        when(productService.findAllByCorrespondingCategory(category.getId())).thenReturn(dummyProductList);

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
                .andExpect(jsonPath("$.products[1].name", is("post-its")))
                .andExpect(jsonPath("$.products[1].price", is(1.00)));

        verify(productService, times(1)).findAllByCorrespondingCategory(category.getId());
    }
}
