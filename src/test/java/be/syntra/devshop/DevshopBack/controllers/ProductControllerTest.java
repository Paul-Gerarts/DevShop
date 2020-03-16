package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.services.ProductServiceImpl;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.GeneralUtils.asJsonString;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createProductDto;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createProductList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private ProductController productController;

    @Mock
    private ProductDto productDto;

    @Mock
    private ProductMapperUtility mapperUtility;

    @Test
    void addProductTest() throws Exception {
        // Given
        ProductDto newProduct = createProductDto();
        // When
        when(productService.save(any(ProductDto.class))).thenReturn(newProduct);
        // Then
        mockMvc.perform(post("/products")
                .content(asJsonString(newProduct))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void allProductsEndPointTest() throws Exception {
        // Given
        List<Product> products = createProductList();
        ProductDto singleProduct = ProductMapperUtility.convertToProductDto(products.get(0));
        // When
        when(productService.save(singleProduct)).thenReturn(singleProduct);
        when(productService.findAll()).thenReturn(products);
        // Then
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

}
