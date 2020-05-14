package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.models.ProductPageAndMinMaxPrice;
import be.syntra.devshop.DevshopBack.models.SearchModel;

public interface SearchService {

    ProductPageAndMinMaxPrice applySearchModel(SearchModel searchModel);
}
