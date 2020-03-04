package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.config.DTOMapper;
import be.syntra.devshop.DevshopBack.model.ProductDTO;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;
    private DTOMapper dtoMapper;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @PostMapping
    public ResponseEntity<?> postProductToDB(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productRepository.save(dtoMapper.convertToProduct(productDTO)));
    }

}
