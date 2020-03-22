package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.dtos.ProductDto;
import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.services.ProductServiceImpl;
import be.syntra.devshop.DevshopBack.testutilities.JsonUtils;
import be.syntra.devshop.DevshopBack.testutilities.ProductUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(JsonUtils.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @MockBean
    private ProductServiceImpl productService;

    @Test
    void testRetrieveAllProductsEndpoint() throws Exception {
        // given
        List<Product> productList = ProductUtils.createDummyProductList();
        Mockito.when(productService.findAll()).thenReturn(productList);
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

        Mockito.verify(productService, times(1)).findAll();
    }

    @Test
    void createProductEndpoint() throws Exception {
        // given
        ProductDto productDtoDummy = ProductUtils.createProductDto();
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

        Mockito.verify(productService, times(1)).save(productDtoDummy);

    }
}
