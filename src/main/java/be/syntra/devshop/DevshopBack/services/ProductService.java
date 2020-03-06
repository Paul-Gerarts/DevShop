package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    void productToDataBase(Product product);
}
