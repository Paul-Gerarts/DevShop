package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<?> retrieveAllNonArchivedProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllByArchivedFalse());
    }

    /*
     *@Returns: 201-created code when our product's successfully saved
     */
    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        productService.save(productDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productDto);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> findProductById(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findById(id));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto product) {
        productService.save(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(product);
    }

    @GetMapping("/archived")
    public ResponseEntity<?> retrieveAllArchivedProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllByArchivedTrue());
    }

    @GetMapping("/search/{searchRequest}")
    public ResponseEntity<?> retrieveAllProductsBySearchRequest(@PathVariable String searchRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllByNameContainingIgnoreCaseAndArchivedFalse(searchRequest));
    }

}
