package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> retrieveAllProducts() {
        return ResponseEntity
                .status(200)
                .body(productService.findAll());
    }

    /*
     *@Returns: 201-created code when our product's successfully saved
     */
    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        productService.save(productDto);
        return ResponseEntity
                .status(201)
                .body(productDto);
    }

}
