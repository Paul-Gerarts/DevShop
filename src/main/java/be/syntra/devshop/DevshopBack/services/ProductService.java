package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.dtos.ProductDto;
import be.syntra.devshop.DevshopBack.entities.Product;

import java.util.List;


public interface ProductService {

    ProductDto save(ProductDto productDTO);

    List<Product> findAll();
}
