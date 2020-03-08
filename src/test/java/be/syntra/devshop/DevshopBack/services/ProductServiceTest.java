package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createProductList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductServiceImpl.class)
public class ProductServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @Test
    void getAllProductsTest() {
        // Given
        List<Product> products = createProductList();
        // When
        when(productService.findAll()).thenReturn(products);
        // Then
        assertThat(productService.findAll().size()).isEqualTo(products.size());
    }
}
