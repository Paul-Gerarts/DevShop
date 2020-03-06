package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.ProductDTO;
import be.syntra.devshop.DevshopBack.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<?> postProductToDB(@RequestBody ProductDTO productDTO) {
        productService.productToDataBase(productDTO);
        return ResponseEntity.ok(productDTO);
    }

}
