package be.syntra.devshop.DevshopBack.models;

import be.syntra.devshop.DevshopBack.entities.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductList {

    private List<Product> products;

}
