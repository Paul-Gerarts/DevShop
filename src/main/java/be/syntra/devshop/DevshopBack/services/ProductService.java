package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.ProductDto;
import be.syntra.devshop.DevshopBack.models.ProductList;


public interface ProductService {

    ProductDto save(ProductDto productDTO);

    ProductList findAll();

    ProductList findAllByCorrespondingCategory(Long id);

    ProductList findAllByArchivedFalse();

    ProductList findAllByArchivedTrue();

    ProductList findAllByNameContainingIgnoreCaseAndArchivedFalse(String searchRequest);

    Product findById(Long id);
}
