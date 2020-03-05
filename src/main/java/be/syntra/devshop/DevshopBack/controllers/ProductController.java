package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.model.ProductDTO;
import be.syntra.devshop.DevshopBack.repositories.ProductRepository;
import be.syntra.devshop.DevshopBack.services.DTOMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;
    private DTOMapperService dtoMapperService;

    public ProductController(ProductRepository productRepository, DTOMapperService dtoMapperService) {
        this.productRepository = productRepository;
        this.dtoMapperService = dtoMapperService;
    }


    @PostMapping
    public ResponseEntity<?> postProductToDB(@RequestBody ProductDTO productDTO) {
        productRepository.save(dtoMapperService.convertToProduct(productDTO));
        return ResponseEntity.ok(productDTO);
    }

}
