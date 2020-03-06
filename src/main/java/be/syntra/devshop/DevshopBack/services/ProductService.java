package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.ProductDTO;


public interface ProductService {
    void productToDataBase(ProductDTO productDTO);
}
