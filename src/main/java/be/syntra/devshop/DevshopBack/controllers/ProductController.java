package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.services.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    /*
     *@Returns: 201-created code when our product's succesfully saved
     */
    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        productService.save(productDto);
        return ResponseEntity
                .status(201)
                .body(productDto);
    }

}
