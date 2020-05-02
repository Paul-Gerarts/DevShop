package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.models.SearchModelDto;
import be.syntra.devshop.DevshopBack.services.CategoryService;
import be.syntra.devshop.DevshopBack.services.ProductService;
import be.syntra.devshop.DevshopBack.services.SearchService;
import be.syntra.devshop.DevshopBack.services.utilities.ProductMapperUtility;
import be.syntra.devshop.DevshopBack.services.utilities.SearchModelMapperUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SearchModelMapperUtility searchModelMapperUtility;
    private final SearchService searchService;
    private final ProductMapperUtility productMapperUtility;

    @Autowired
    public ProductController(
            ProductService productService,
            CategoryService categoryService,
            SearchModelMapperUtility searchModelMapperUtility,
            SearchService searchService,
            ProductMapperUtility productMapperUtility
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.searchModelMapperUtility = searchModelMapperUtility;
        this.searchService = searchService;
        this.productMapperUtility = productMapperUtility;
    }

    @GetMapping()
    public ResponseEntity<ProductList> retrieveAllNonArchivedProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productMapperUtility.convertToProductListObject(productService.findAllByArchivedFalse()));
    }

    /*
     *@Returns: 201-created code when our product's successfully saved
     */
    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        productService.save(productDto);
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
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto product) {
        productService.save(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(product);
    }

    @GetMapping("/archived")
    public ResponseEntity<ProductList> retrieveAllArchivedProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productMapperUtility.convertToProductListObject(productService.findAllByArchivedTrue()));
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryList> retrieveAllCategories() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findAll());
    }

    @GetMapping("/search/{searchRequest}")
    public ResponseEntity<ProductList> retrieveAllProductsBySearchRequest(@PathVariable String searchRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest));
    }

    @PostMapping("/searching")
    public ResponseEntity<ProductList> retrieveAllProductsBySearchModel(@RequestBody SearchModelDto searchModelDto){
        log.info("retrieveAllProductsBySearchModel -> {}",searchModelDto.isArchivedView());
        searchService.setSearchModel(searchModelMapperUtility.convertToSearchModel(searchModelDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productMapperUtility.convertToProductListObject(productService.findAllBySearchModel()));
    }

}
