package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.ProductPageAndMaxPrice;
import be.syntra.devshop.DevshopBack.models.SearchModel;

public interface SearchService {

    ProductPageAndMaxPrice applySearchModel(SearchModel searchModel);
}
