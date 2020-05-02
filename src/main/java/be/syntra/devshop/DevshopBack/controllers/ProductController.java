package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.CategoryList;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;
import be.syntra.devshop.DevshopBack.services.CategoryService;
import be.syntra.devshop.DevshopBack.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(
            ProductService productService,
            CategoryService categoryService
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<ProductList> retrieveAllNonArchivedProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllByArchivedFalse());
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
                .body(productService.findAllByArchivedTrue());
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryList> retrieveAllCategories() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findAll());
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Long> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(id);
    }

    @GetMapping("/search/{searchRequest}")
    public ResponseEntity<ProductList> retrieveAllProductsBySearchRequest(@PathVariable String searchRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest));
    }
}
