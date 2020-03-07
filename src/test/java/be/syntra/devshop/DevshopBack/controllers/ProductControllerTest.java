package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.services.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.TestUtils.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
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

    private ProductDto createProduct() {
        return ProductDto.builder()
                .name("post-its")
                .price(BigDecimal.valueOf(1.00))
                .build();
    }

    private List<ProductDto> createProductList() {
        List<ProductDto> products = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            products.add(createProduct());
        }
        return products;
    }

    @Test
    void addProductTest() throws Exception {
        // Given
        ProductDto newProduct = createProduct();
        // When
        when(productService.save(any(ProductDto.class))).thenReturn(newProduct);
        // Then
        mockMvc.perform(post("/products")
                .content(asJsonString(newProduct))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllProductsTest() {
        // Given
        List<ProductDto> products = createProductList();
        // When
        when(productService.findAll()).thenReturn(products);
        // Then
        assertThat(productService.findAll().size()).isEqualTo(products.size());
    }

    @Test
    void allProductsEndPointTest() throws Exception {
        // Given
        List<ProductDto> products = createProductList();
        ProductDto singleProduct = products.get(0);
        // When
        when(productService.save(singleProduct)).thenReturn(singleProduct);
        when(productService.findAll()).thenReturn(products);
        // Then
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

}
