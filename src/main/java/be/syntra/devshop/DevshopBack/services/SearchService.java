package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Product;
import be.syntra.devshop.DevshopBack.models.SearchModel;

import java.util.List;

public interface SearchService {

    List<Product> applySearchModel(SearchModel searchModel);
}
