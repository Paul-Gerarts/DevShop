package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    void productToDataBase(ProductDTO productDTO);
}
