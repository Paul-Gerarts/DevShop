package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.models.SearchModelDto;
import be.syntra.devshop.DevshopBack.services.CategoryService;
import be.syntra.devshop.DevshopBack.services.ProductService;
import be.syntra.devshop.DevshopBack.services.SearchService;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapper;
import be.syntra.devshop.DevshopBack.services.utilities.SearchModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SearchService searchService;
    private final SearchModelMapper searchModelMapper;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(
            ProductService productService,
            CategoryService categoryService,
            SearchService searchService,
            SearchModelMapper searchModelMapper,
            ProductMapper productMapper
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.searchService = searchService;
        this.searchModelMapper = searchModelMapper;
        this.productMapper = productMapper;
    }

    /*
     *@Returns: 201-created code when our product's successfully saved
     */
    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        productService.save(productMapper.convertToProduct(productDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDto);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findById(id));
    }

    @PostMapping("/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        productService.save(productMapper.convertToProduct(productDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryList> retrieveAllCategories() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findAll());
    }

    @PostMapping("/searching")
    public ResponseEntity<ProductList> retrieveAllProductsBySearchModel(@RequestBody SearchModelDto searchModelDto) {
        log.info("retrieveAllProductsBySearchModel -> searchModel{}", searchModelDto);
        final List<Product> productList = searchService.applySearchModel(
                searchModelMapper.convertToSearchModel(searchModelDto)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productMapper.convertToProductListObject(productList)
                );
    }

}
