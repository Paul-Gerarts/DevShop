package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.factories.SecurityUserFactory;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.security.configuration.CorsConfiguration;
import be.syntra.devshop.DevshopBack.security.configuration.WebSecurityConfig;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAccessDeniedHandler;
import be.syntra.devshop.DevshopBack.security.jwt.JWTAuthenticationEntryPoint;
import be.syntra.devshop.DevshopBack.security.jwt.JWTTokenProvider;
import be.syntra.devshop.DevshopBack.security.services.SecurityUserService;
import be.syntra.devshop.DevshopBack.services.ProductServiceImpl;
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

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({JsonUtils.class, WebSecurityConfig.class, CorsConfiguration.class, JWTTokenProvider.class, JWTAuthenticationEntryPoint.class, JWTAccessDeniedHandler.class})
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @MockBean
    private ProductServiceImpl productService;

    @Mock
    private SecurityUserFactory securityUserFactory;

    @MockBean
    private SecurityUserService securityUserService;

    @Test
    @WithMockUser
    void testRetrieveAllNonArchivedProductsEndpoint() throws Exception {
        // given
        List<Product> productList = createDummyNonArchivedProductList();
        when(productService.findAllByArchivedFalse()).thenReturn(productList);
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/products")
                );
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[1].name", is("product")))
                .andExpect(jsonPath("$[1].price", is(110)));

        verify(productService, times(1)).findAllByArchivedFalse();
    }

    @Test
    @WithMockUser
    void testRetrieveAllArchivedProductsEndpoint() throws Exception {
        // given
        List<Product> productList = createDummyArchivedProductList();
        when(productService.findAllByArchivedTrue()).thenReturn(productList);
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/products/archived")
                );
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(equalTo("test")))
                .andExpect(jsonPath("$[0].price").value(equalTo(55.99)))
                .andExpect(jsonPath("$[0].archived", is(true)))
                .andExpect(jsonPath("$[1].name", is("product")))
                .andExpect(jsonPath("$[1].price", is(110)))
                .andExpect(jsonPath("$[1].archived", is(true)));

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
    void testRetrieveAllProductsFoundBySearchRequestTest() throws Exception {
        // given
        String searchRequest = "POst";
        List<Product> dummyProductList = List.of(createNonArchivedProduct());
        when(productService.findAllByNameContainingIgnoreCase(searchRequest)).thenReturn(dummyProductList);
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/products/search/" + searchRequest)
                );
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(equalTo("post-its")))
                .andExpect(jsonPath("$[0].price").value(equalTo(1.00)));

        verify(productService, times(1)).findAllByNameContainingIgnoreCase(searchRequest);
    }
}
