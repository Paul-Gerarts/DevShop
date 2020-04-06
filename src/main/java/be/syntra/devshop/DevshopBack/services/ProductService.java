package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;

import java.util.List;


public interface ProductService {

    ProductDto save(ProductDto productDTO);

    List<Product> findAll();

    List<Product> findAllByArchivedFalse();

    List<Product> findAllByArchivedTrue();

    Product findById(Long id);
}
