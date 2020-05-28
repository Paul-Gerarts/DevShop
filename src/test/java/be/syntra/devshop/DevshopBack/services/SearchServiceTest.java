package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductPage;
import be.syntra.devshop.DevshopBack.models.SearchModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyPageable;
import static be.syntra.devshop.DevshopBack.testutilities.ProductUtils.createDummyProductPage;
import static be.syntra.devshop.DevshopBack.testutilities.SearchModelUtils.getDummySearchModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SearchServiceTest {

    @Mock
    ProductService productService;

    @InjectMocks
    SearchServiceImpl searchService;

    @Test
    void canApplySearchModelSearchTest() {
        // given
        Pageable pageable = createDummyPageable();
        SearchModel searchModel = getDummySearchModel();
        Page<Product> page = createDummyProductPage();
        when(productService.findAllBySearchModel(pageable, searchModel)).thenReturn(page);

        // when
        ProductPage result = searchService.applySearchModel(searchModel);

        // then
        assertThat(result.getProductPage()).isEqualTo(page);
        verify(productService, times(1)).findAllBySearchModel(pageable, searchModel);
    }
}