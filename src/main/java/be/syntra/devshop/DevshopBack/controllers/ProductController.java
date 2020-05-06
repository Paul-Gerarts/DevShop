package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.*;
import be.syntra.devshop.DevshopBack.services.CategoryService;
import be.syntra.devshop.DevshopBack.services.ProductService;
import be.syntra.devshop.DevshopBack.services.utilities.CategoryMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CategoryMapperUtility categoryMapperUtility;

    @Autowired
    public ProductController(
            ProductService productService,
            CategoryService categoryService,
            CategoryMapperUtility categoryMapperUtility
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.categoryMapperUtility = categoryMapperUtility;
    }

    @GetMapping()
    public ResponseEntity<ProductList> retrieveAllNonArchivedProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllByArchivedFalse());
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<ProductList> retrieveAllWithCorrespondingCategory(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllByCorrespondingCategory(id));
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

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> findCategoryBy(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryMapperUtility.mapToCategoryDto(categoryService.findById(id)));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/categories/set_category")
    public ResponseEntity<CategoryChangeDto> setNewCategoryForProducts(@RequestBody CategoryChangeDto categoryChangeDto) {
        productService.setNewCategory(categoryChangeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryChangeDto);
    }

    @PostMapping("/categories/update_category")
    public ResponseEntity<CategoryChangeDto> updateCategory(@RequestBody CategoryChangeDto categoryChangeDto) {
        categoryService.updateCategory(categoryChangeDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryChangeDto);
    }

    @GetMapping("/search/{searchRequest}")
    public ResponseEntity<ProductList> retrieveAllProductsBySearchRequest(@PathVariable String searchRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest));
    }
}
