package be.syntra.devshop.DevshopBack.controllers;

import be.syntra.devshop.DevshopBack.models.ProductDTO;
import be.syntra.devshop.DevshopBack.services.ProductService;
import be.syntra.devshop.DevshopBack.services.mappers.DTOToEntityMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private DTOToEntityMapperService dtoToEntityMapperService;

    public ProductController(ProductService productService, DTOToEntityMapperService dtoToEntityMapperService) {
        this.productService = productService;
        this.dtoToEntityMapperService = dtoToEntityMapperService;
    }


    @PostMapping
    public ResponseEntity<?> postProductToDB(@RequestBody ProductDTO productDTO) {
        productService.productToDataBase(dtoToEntityMapperService.convertToProduct(productDTO));
        return ResponseEntity.ok(productDTO);
    }

}
